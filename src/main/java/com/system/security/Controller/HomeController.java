package com.system.security.Controller;

import com.system.security.Model.Cart;
import com.system.security.Model.Products;
import com.system.security.Model.User;
import com.system.security.Repository.CartRepository;
import com.system.security.SecurityConfig.IAuthenticationFacade;
import com.system.security.Service.CartService;
import com.system.security.Service.OrderedService;
import com.system.security.Service.ProductsService;
import com.system.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderedService orderedService;
    @Autowired
    private CartService cartService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    @Lazy
    private UserService userServices;




    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView showHomePost(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        User user = userServices.findUserByUserName(username);
        modelAndView.addObject("CartSize",cartService.getCartByUserFacade(user).size());
        modelAndView.addObject("products",productsService.getAllProducts());
 modelAndView.addObject("productCategories",productsService.getAllProductCategories());

        modelAndView.setViewName("home");
        return modelAndView;
    }

public ModelAndView showCart(Principal principal){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("Size",cartService.getCartByUser(principal).size());
        modelAndView.setViewName("addToCartHeader");
        return modelAndView;
}



@RequestMapping(value = "/AddToCart/RemoveProduct/{id}",method = RequestMethod.GET)
    public String deleteAddToCart(@PathVariable int id){
cartRepository.deleteById(id);
        return "redirect:/";
}
    @RequestMapping(value = "/AddToCart/Search/{productName}",method = RequestMethod.GET)
    public ModelAndView fetchSearchProduct(@PathVariable String productName){
ModelAndView modelAndView = new ModelAndView();
Products products=productsService.findByProductName(productName);
modelAndView.addObject("product",products);
modelAndView.setViewName("SearchPost");
return modelAndView;

    }

    @RequestMapping(value = "/AddToCart/addProduct/{id}",method = RequestMethod.GET)
    public String createCart(@PathVariable int id, Cart cart, Principal principal){
    User user=cartService.getUserFromPrinciple(principal);

        cart.setUser(user);
        cart.setProducts(productsService.findByProductId(id));

      cart.setTotal(user.getCart().stream().map(e->e.getTotal().multiply(cart.getProducts().getPrice()))
              .reduce(BigDecimal::add)
              .orElse(BigDecimal.ZERO));


        cartRepository.save(cart);
        cartService.SaveUserCart(principal);


        return "redirect:/";
    }


}
