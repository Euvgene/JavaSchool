package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "productTitle")
    private String productTitle;

    @Column(name = "price")
    private BigDecimal productPrice;

    @Column(name = "category")
    private byte category;

    @OneToOne()
    @JoinColumn(name = "product_param")
    private Parameters productParams;


    @Column(name = "quantity")
    private byte productQuantity;
}
