package com.system.security.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ReviewId;
    @Lob
    private String comment;

    @Column(name = "Rating")
    private Integer rating ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Order_Date")
    private Date date;

     @ManyToOne
     private User user;

    @ManyToOne
    private Products products;


}
