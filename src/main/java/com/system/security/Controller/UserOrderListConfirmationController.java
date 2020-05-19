package com.system.security.Controller;


import com.system.security.Model.OrderList;
import com.system.security.Model.User;
import com.system.security.Repository.OrderListRepository;
import com.system.security.SecurityConfig.IAuthenticationFacade;
import com.system.security.Service.OrderedService;
import com.system.security.Service.ProductsService;
import com.system.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/confirmationPage")
public class UserOrderListConfirmationController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    @Lazy
    private UserService userServices;
    @Autowired
    private OrderedService orderedService;
    @Autowired
    private OrderListRepository orderListRepository;




    @RequestMapping(path = "/creating",method = RequestMethod.POST)
    public ModelAndView creatingOrderList(@ModelAttribute("orderList") OrderList orderList, BindingResult Result,Principal principal)  {
        ModelAndView modelAndView = new ModelAndView();
        if(Result.hasErrors()){
            modelAndView.setViewName("confirmationPage");
    return modelAndView;
}
        Date d1= new Date();
        orderList.setOrderDate(d1);
    orderList.setTotal(productsService.getTotalCollection());
    orderList.setUser(orderedService.fetchUser(principal));
    orderList.setProducts(productsService.getProductsCollection());
    User user =orderedService.getUserFromPrinciple(principal);
    user.getOrderList().add(orderList);
    orderListRepository.save(orderList);
    modelAndView.setViewName("redirect:/profile");
        productsService.removeAllProductFromCollection();
        return modelAndView;
    }

@GetMapping
    public String showConfirmationForm(Model model, OrderList orderList){
model.addAttribute("addToCartProducts",productsService.getProductsCollection());
Authentication authentication = authenticationFacade.getAuthentication();
String username = authentication.getName();
User user=userServices.findUserByUserName(username);
model.addAttribute("Total",productsService.getTotalCollection().toString());
model.addAttribute("CurrentUser",user);

        return "confirmationPage";}


}
