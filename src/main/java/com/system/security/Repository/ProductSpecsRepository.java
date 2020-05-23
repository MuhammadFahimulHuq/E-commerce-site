package com.system.security.Repository;

import com.system.security.Model.ProductSpecs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecsRepository extends JpaRepository<ProductSpecs,Integer> {
}
