package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductDto {
    private int productId;
    private BigDecimal productPrice;
    private String fotoId;
    private String name;
    private String gender;
    private byte age;
    private String lifeSpan;
    private byte productQuantity;

    public ProductDto(Product product) {
        this.productId= product.getProductId();
        this.fotoId = product.getFotoId();
        this.productPrice = product.getProductPrice();
        this.name = product.getProductTitle();
        this.gender = product.getProductParams().getProductGender();
        this.age = product.getProductParams().getProductAge();
        this.lifeSpan = product.getProductParams().getProductLifespan();
        this.productQuantity= product.getProductQuantity();
    }
}
