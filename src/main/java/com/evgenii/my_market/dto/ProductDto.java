package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDto {
int fotoId;
String name;
String gender;
String birthday;
String lifeSpane;

    public ProductDto(Product p) {
        this.fotoId = p.getProductId();
        this.name = p.getProductTitle();
        this.gender = p.getProductParams().getProductGender();
        this.birthday = p.getProductParams().getProductBirthday();
        this.lifeSpane = p.getProductParams().getProductLifespan();
    }
}
