package com.evgenii.my_market.entity;

import com.evgenii.my_market.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @ManyToOne()
    @JoinColumn(name = "category")
    private Category category;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_param")
    private Parameters productParams;

    @Column(name = "foto_id")
    private String fotoId;

    @Column(name = "quantity")
    @Min(value = 0)
    private byte productQuantity;

    public Product(ProductDto productDto) {
        this.productId = productDto.getProductId();
        this.fotoId = productDto.getFotoId();
        this.productPrice = productDto.getProductPrice();
        this.productTitle = productDto.getProductTitle();
        this.category = productDto.getCategory();
        this.productParams = productDto.getParameters();
        this.productQuantity = productDto.getProductQuantity();
    }

    public void decrementQuantityProduct(byte productQuantity) {
        this.productQuantity -= productQuantity;
    }

    public void incrementQuantityProduct(byte productQuantity) {
        this.productQuantity += productQuantity;
    }
}
