package com.system.security.Repository;

import com.system.security.Model.Cart;
import com.system.security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart ,Integer> {


    List<Cart> findAllByUserId(int id);

    List<Cart> findAllByUser(User user);
}
