package com.evgenii.my_market.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "parameters")
@Data
@NoArgsConstructor
public class Parameters {

    @Id
    @Column(name = "parameters_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parametersId;

    @Column(name = "gender")
    @Pattern(regexp = "^[0-9A-Za-z]+$", message ="Please provide a gender" )
    @NotEmpty(message = "Please provide a gender")
    private String productGender;

    @Column(name = "age")
    @NotNull(message = "Please provide age")
    @Positive(message ="Age must be positive" )
    private byte productAge;

    @Column(name = "weight")
    @NotNull(message = "Please provide weight")
    @Positive(message ="Weight must be positive" )
    private short productWeight;

    @Column(name = "lifespan")
    @NotEmpty(message = "Please provide lifespan")
    private String productLifespan;


}
