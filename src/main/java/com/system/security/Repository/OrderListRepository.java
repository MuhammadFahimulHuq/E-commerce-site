package com.system.security.Repository;

import com.system.security.Model.OrderList;
import com.system.security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrderListRepository extends JpaRepository<OrderList,Integer> {




    Collection<OrderList> findAllByUser(User user);
}
