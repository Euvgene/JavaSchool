package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

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

    @ManyToOne
    @JoinColumn(name = "order_state")
    private OrderState orderState;


    public Order(Cart cart, User user, Address address) {
        this.items = new ArrayList<>();
        this.owner = user;
        this.address = address;
        this.price = cart.getPrice();
        for (CartItem ci : cart.getCartItems()) {
            OrderItem oi = new OrderItem(ci);
            oi.setOrder(this);
            this.items.add(oi);
        }
    }

}
