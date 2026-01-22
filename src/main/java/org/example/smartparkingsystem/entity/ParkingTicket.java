package org.example.smartparkingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String status; // ACTIVE / COMPLETED

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private ParkingSlot slot;
}
