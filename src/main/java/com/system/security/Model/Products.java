package com.system.security.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product")

public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_name")
    private String productName;
//    @Lob @Column(name = "product_description")
//    private String description;
//    @Column(name = "product_quantity")
//    private int quantity;
    @Column(name = "product_price")
    private BigDecimal price;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "product_images",columnDefinition="BINARY(500000)")
    private byte[] productImage;

    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getProductImage());
    }

    @ManyToMany
    private Collection<OrderList>  orderList;

}
