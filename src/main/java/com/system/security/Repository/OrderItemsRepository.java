package com.system.security.Repository;

import com.system.security.Model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository  extends JpaRepository<OrderItems,Integer> {
}
