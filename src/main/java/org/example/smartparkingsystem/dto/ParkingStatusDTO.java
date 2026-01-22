package org.example.smartparkingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParkingStatusDTO {
    private String vehicleNumber;
    private String slotNumber;
    private String status;        // ACTIVE / COMPLETED
    private LocalDateTime entryTime;
}
