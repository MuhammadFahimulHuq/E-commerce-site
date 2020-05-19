package com.system.security.Service;

import com.system.security.Model.OrderList;
import com.system.security.Model.Products;
import com.system.security.Model.User;
import com.system.security.Repository.OrderListRepository;
import com.system.security.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public User fetchUser(Principal principal) {
        User user = getUserFromPrinciple(principal);
        return user;
    }

public List<OrderList> findAllbyOrderListId(Collection<Integer> id){return orderListRepository.findAllById(id);}

    public Collection<OrderList>  fetchOrder(Principal principal) {

    return fetchUser(principal).getOrderList();}

    public Collection<Integer> orderIdByUserId(Principal principal){
     return  fetchUser(principal).getOrderList().stream().map(entry->entry.getOrderId()).collect(Collectors.toList());
    }
    public List<OrderList> orderListCollection(Principal principal){return findAllbyOrderListId(orderIdByUserId(principal));}

    public List<Collection<Products>> getProduct(Principal principal){
     List<OrderList>orderList= findAllbyOrderListId(orderIdByUserId(principal));
    return orderList.stream().map(entry->entry.getProducts()).collect(Collectors.toList());

    }
public List<Products>fetchproductid(Principal principal){
       return getProduct(principal).stream().flatMap(entry->entry.stream()).collect(Collectors.toList());
}
public List<Integer> fetchid(Principal principal){
   return    fetchproductid(principal).stream().map(entry->entry.getId()).collect(Collectors.toList());
}

public Collection<Products> fetchProduct(Principal principal){
        return productsService.findByCollectionId(fetchid(principal));
}
public BigDecimal TotalProducts(Principal principal){
        return fetchProduct(principal).stream().map(entry->entry.getPrice())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
}
}