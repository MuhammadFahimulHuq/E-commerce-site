package com.system.security.Controller;


import com.system.security.Model.Cart;
import com.system.security.Model.OrderItems;
import com.system.security.Model.OrderList;
import com.system.security.Model.User;
import com.system.security.Repository.OrderItemsRepository;
import com.system.security.Repository.OrderListRepository;
import com.system.security.SecurityConfig.IAuthenticationFacade;
import com.system.security.Service.CartService;
import com.system.security.Service.OrderedService;
import com.system.security.Service.ProductsService;
import com.system.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
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
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private CartService cartService;




    @RequestMapping(path = "/creating",method = RequestMethod.POST)
    public ModelAndView creatingOrderList(@ModelAttribute("orderList") OrderList orderList, BindingResult Result,Principal principal)  {
        ModelAndView modelAndView = new ModelAndView();
        if(Result.hasErrors()){
            modelAndView.setViewName("confirmationPage");
    return modelAndView;}
       User user = orderedService.getUserFromPrinciple(principal);
    Collection<Cart> cart = user.getCart();
        if (cart == null) {
        throw new IllegalArgumentException("Cart not found");
    }

        OrderList saveOrder = new OrderList();
        Date d1= new Date();
        saveOrder.setOrderDate(d1);
        saveOrder.setUser(user);
        saveOrder.setCustomerAddress(orderList.getCustomerAddress());
        saveOrder.setCreditCardPassword(orderList.getCreditCardPassword());
        saveOrder.setCustomerPhone(orderList.getCustomerPhone());
        saveOrder.setCreditCard(orderList.getCreditCard());
        saveOrder.setOrderItems(new ArrayList<>());

        for(Cart i: cart){

            OrderItems orderItems = new OrderItems();

         orderItems.setProducts(i.getProducts());
            orderItems.setOrderList(saveOrder);
           saveOrder.getOrderItems().add(orderItems);


        }
        user.getOrderList().add(saveOrder);
        orderListRepository.save(saveOrder);
cartService.removeUserCart(cart);
    modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }

@GetMapping
    public ModelAndView showConfirmationForm(OrderList orderList,Principal principal) {
ModelAndView modelAndView = new ModelAndView();

    Authentication authentication = authenticationFacade.getAuthentication();
    String username = authentication.getName();
    User user = userServices.findUserByUserName(username);
    modelAndView.addObject("Carts", cartService.getCartByUser(principal));
    modelAndView.addObject("totalAmountOfCart",cartService.getTotalFromCart(principal));
    modelAndView.addObject("CurrentUser",user);
modelAndView.setViewName("confirmationPage");
return modelAndView;
}



}
