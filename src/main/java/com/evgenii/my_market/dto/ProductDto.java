package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public ProductDto(Product p) {
        this.productId= p.getProductId();
        this.fotoId = p.getFotoId();
        this.productPrice = p.getProductPrice();
        this.name = p.getProductTitle();
        this.gender = p.getProductParams().getProductGender();
        this.age = p.getProductParams().getProductAge();
        this.lifeSpan = p.getProductParams().getProductLifespan();
    }
}
