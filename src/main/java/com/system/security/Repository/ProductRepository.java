package com.system.security.Repository;

import com.system.security.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Integer> {
    Products findByProductName(String ProductName);


}
