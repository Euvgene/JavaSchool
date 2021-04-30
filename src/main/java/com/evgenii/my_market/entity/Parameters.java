package com.evgenii.my_market.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "parameters")
@Data
@NoArgsConstructor
public class Parameters  {

    @Id
    @Column(name = "parameters_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parametersId;

    @Column(name = "gender")
    private String productGender;

    @Column(name = "age")
    private byte productAge;

    @Column(name = "weight")
    private short productWeight;

    @Column(name = "lifespan")
    private String productLifespan;


}
