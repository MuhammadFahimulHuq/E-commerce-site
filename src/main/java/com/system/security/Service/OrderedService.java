package com.system.security.Service;

import com.system.security.Model.OrderList;
import com.system.security.Model.User;
import com.system.security.Repository.OrderListRepository;
import com.system.security.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderedService {
    @Autowired
    private OrderListRepository orderListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductsService productsService;


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

public Collection<OrderList> getAllOrderFromUser(Principal principal){
       User user= getUserFromPrinciple(principal);
       return orderListRepository.findAllByUser(user);
}


}