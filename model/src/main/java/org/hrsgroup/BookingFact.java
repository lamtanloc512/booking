package org.hrsgroup;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "booking_facts")
public class BookingFact extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long hotelId;
    private Long userId;
    private Long addressId;
    private Long hotelRoomId;
}
