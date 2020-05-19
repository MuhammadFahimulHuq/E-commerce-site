package com.system.security.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "OrderList")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Order_Id")
    private int orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Order_Date")
    private Date orderDate;

    @Column(name = "Customer_Address",nullable = false)
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    @Size(min = 3, max = 240)
    private String customerAddress;

    @Column(name = "Customer_Phone",nullable = false)
    @Pattern(regexp = "[0-9]+")
    @Size(min = 11, max = 12)
    private String customerPhone;

    @Column(name = "Customer_CreditCard")
    private String CreditCard;

    @Column(name="Customer_CreditCardPINCODE")
    private String CreditCardPassword;

    @Column(name = "Total_amount",nullable = false)
    private BigDecimal Total;


//    @MapKeyColumn(name = "key")
//    @OneToMany(cascade = CascadeType.MERGE,targetEntity = Products.class,fetch = FetchType.LAZY)
//    private Map<Products,Integer> productsIntegerHashMap = new HashMap<>();
//
    @ManyToOne
    private User user;


    @ManyToMany(targetEntity = Products.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<Products> products = new ArrayList<>();
}
