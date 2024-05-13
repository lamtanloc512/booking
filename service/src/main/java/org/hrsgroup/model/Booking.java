package org.hrsgroup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Hotel hotel;
    @NotNull
    private User user;
    @NotNull
    private Set<Room> rooms;
    @NotNull
    private Double totalPrice;
}
