package org.hrsgroup;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String number;
    private Integer capacity;
    private Float rating;
    private Float amenities;
    private Double price;
}
