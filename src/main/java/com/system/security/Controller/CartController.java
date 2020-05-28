package com.system.security.Controller;

import com.system.security.Model.Cart;
import com.system.security.Model.User;
import com.system.security.Repository.CartRepository;
import com.system.security.Service.CartService;
import com.system.security.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller

public class CartController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @RequestMapping(value = "/cart",method = RequestMethod.GET)
    public ModelAndView getCart(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Carts",cartService.getCartByUser(principal));
        modelAndView.addObject("CartSize",cartService.getCartByUser(principal).size());
        modelAndView.addObject("totalAmountOfCart",cartService.getTotalFromCart(principal));
        modelAndView.setViewName("cart");
        return modelAndView;
    }
    @RequestMapping(value = "/cart/AddCart/{id}",method = RequestMethod.GET)
    public ModelAndView createCart(@PathVariable int id, Cart cart, Principal principal){
        User user=cartService.getUserFromPrinciple(principal);
        cart.setUser(user);
        cart.setProducts(productsService.findByProductId(id));

        cartRepository.save(cart);
        cartService.SaveUserCart(principal);
        return getCart(principal);
    }
    @RequestMapping(value = "/cart/RemoveProduct/{id}",method = RequestMethod.GET)
    public ModelAndView deleteAddToCart(@PathVariable int id,Principal principal){
        cartRepository.deleteById(id);
        return getCart(principal);
    }


}
