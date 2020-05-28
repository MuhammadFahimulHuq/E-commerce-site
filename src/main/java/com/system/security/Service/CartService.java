package com.system.security.Service;

import com.system.security.Model.Cart;
import com.system.security.Model.User;
import com.system.security.Repository.CartRepository;
import com.system.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }

    public void removeUserCart(Collection<Cart> cart){

        cartRepository.deleteAll(cart);
    }

    public void SaveUserCart(Principal principal) {
        User user= getUserFromPrinciple(principal);
        user.setCart(getCartByUser(principal));
        cartRepository.saveAll(user.getCart()) ;
    }
    public List<Cart> getCartByUser(Principal principal){
        return cartRepository.findAllByUserId(getUserFromPrinciple(principal).getId());
    }
    public List<Cart> getCartByUserFacade(User user){
        return cartRepository.findAllByUser(user);
    }
    public User getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }
        User user = userRepository.findByUserName(principal.getName());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

public BigDecimal getTotalFromCart(Principal principal){
       User user= getUserFromPrinciple(principal);
     return   user.getCart().stream()
             .map(e->e.getProducts().getPrice())
             .reduce(BigDecimal::add)
             .orElse(BigDecimal.ZERO);
}
}
