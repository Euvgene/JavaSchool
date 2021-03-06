package com.evgenii.my_market.entity;

import com.evgenii.my_market.dto.ProductDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;


/**
 * Entity class for products.
 *
 * @author Boznyakov Evgenii
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

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

    /**
     * Constructor for creating new instance of this class.
     *
     * @param productDto an instance of {@linkplain com.evgenii.my_market.dto.ProductDto }
     */
    public Product(ProductDto productDto) {
        this.productId = productDto.getProductId();
        this.fotoId = productDto.getFotoId();
        this.productPrice = productDto.getProductPrice();
        this.productTitle = productDto.getProductTitle();
        this.category = productDto.getCategory();
        this.productParams = productDto.getParameters();
        this.productQuantity = productDto.getProductQuantity();
    }

    /**
     * Decrement quantity of product.
     */
    public void decrementQuantityProduct(byte productQuantity) {
        this.productQuantity -= productQuantity;
    }

    /**
     * Increment quantity of product.
     */
    public void incrementQuantityProduct(byte productQuantity) {
        this.productQuantity += productQuantity;
    }
}
