package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ProductDto {
    private BigDecimal productPrice;
    private int fotoId;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String lifeSpan;

    public ProductDto(Product p) {
        this.fotoId = p.getProductId();
        this.productPrice = p.getProductPrice();
        this.name = p.getProductTitle();
        this.gender = p.getProductParams().getProductGender();
        this.birthday = p.getProductParams().getProductBirthday();
        this.lifeSpan = p.getProductParams().getProductLifespan();
    }
}
