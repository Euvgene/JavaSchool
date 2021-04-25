package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "order_satate")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private int id;

    @Column(name = "state_name")
    private String stateName;

    public OrderState(int id, String stateName) {
        this.id = id;
        this.stateName = stateName;
    }
}
