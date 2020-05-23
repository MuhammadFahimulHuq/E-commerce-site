package com.system.security.Model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductsCategory {

    @Id
    @Column(name = "Categories_id")
    private int id;
    @Column(name = "Categories_Name")
    private String CategoriesName;

    @OneToMany(targetEntity = Products.class,fetch = FetchType.LAZY)
    private Collection<Products> products = new ArrayList<>();
}
