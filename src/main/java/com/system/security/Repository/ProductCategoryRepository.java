package com.system.security.Repository;

import com.system.security.Model.ProductsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductsCategory,Integer> {
}
