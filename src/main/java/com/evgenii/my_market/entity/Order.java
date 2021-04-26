package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "order_id")
    private UUID id;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "user_address")
    private Address address;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "delivery_method")
    private String deliveryMethode;

    @Column(name = "payment_state")
    private boolean paymentState;

    @Column(name = "order_state")
    @Enumerated(EnumType.STRING)
    private StateEnum orderState;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public Order(Cart cart, User user, Address address,
                 String deliveryMethode,String paymentMethod,  boolean paymentState) {
        this.items = new ArrayList<>();
        this.owner = user;
        this.address = address;
        this.deliveryMethode = deliveryMethode;
        this.price = cart.getPrice();
        this.paymentMethod = paymentMethod;
        this.paymentState = paymentState;
        for (CartItem ci : cart.getCartItems()) {
            OrderItem oi = new OrderItem(ci);
            oi.setOrder(this);
            this.items.add(oi);
        }
    }

}