package com.evgenii.my_market.entity;

import com.evgenii.my_market.validator.ValidRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Range(min=1, max=1)
    private Long id;

    @Column(name = "role_name")
    @ValidRole
    private String roleName;

}
