package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.Product Product}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class ProductDto {
    private int productId;
    @NotNull(message = "Please provide a price")
    @Positive(message = "price must be positive")
    private BigDecimal productPrice;

    @NotEmpty(message = "Please provide a foto")
    private String fotoId;

    @NotEmpty(message = "Please provide a product name")
    private String productTitle;

    @Valid
    @NotNull(message = "Please provide category")
    private Category category;
    @Valid
    @NotNull(message = "Please provide parameters")
    private Parameters parameters;
    @NotNull(message = "Please provide quantity")
    @Min(value = 0)
    private byte productQuantity;

    /**
     * Constructor for creating new instance of this class.
     * @param product an instance of {@linkplain com.evgenii.my_market.entity.Product Product}
     */
    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.fotoId = product.getFotoId();
        this.productPrice = product.getProductPrice();
        this.productTitle = product.getProductTitle();
        this.category = product.getCategory();
        this.parameters = product.getProductParams();
        this.productQuantity = product.getProductQuantity();
    }
}
