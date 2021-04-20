package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String streetName;

    @Column(name = "house")
    private int houseNumber;

    @Column(name = "flat")
    private byte flatNumber;
}
