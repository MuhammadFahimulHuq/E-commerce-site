package com.system.security.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ProductSpecs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductSpecs {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;

    @Column(name = "Processor",columnDefinition = "TEXT",length = 512)
    private String Processor;

    @Column(name = "RAM",columnDefinition = "TEXT",length = 512)
    private String RAM;

    @Column(name = "Storage",columnDefinition = "TEXT",length = 512)
    private String Storage;

    @Column(name = "Display",columnDefinition = "TEXT",length = 512)
    private String Display;

    @Column(name = "Camera",columnDefinition = "TEXT",length = 512)
    private String Camera;

    @Column(name = "Battery",columnDefinition = "TEXT",length = 512)
    private String Battery;
}
