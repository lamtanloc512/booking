package org.hrsgroup;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "hotels")
public class Hotel extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Address address;
    private Integer phone;
    private Set<Room> rooms;
}
