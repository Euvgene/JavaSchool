package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    @NotEmpty(message = "Please provide a country")
    private String country;

    @Column(name = "city")
    @NotEmpty(message = "Please provide a city")
    private String city;

    @Column(name = "postal_code")
    @NotEmpty(message = "Please provide a postal code")
    private String postalCode;

    @Column(name = "street")
    @NotEmpty(message = "Please provide a street")
    private String streetName;

    @Column(name = "house")
    @NotNull(message = "Please provide a house number")
    @Positive(message = "house number must be positive")
    private int houseNumber;

    @Column(name = "flat")
    @NotNull(message = "Please provide a flat number")
    @Positive(message = "flat number must be positive")
    private byte flatNumber;
}
