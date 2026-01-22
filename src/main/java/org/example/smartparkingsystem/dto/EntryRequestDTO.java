package org.example.smartparkingsystem.dto;

import lombok.Data;

@Data
public class EntryRequestDTO {

    private String vehicleNumber;   // CAR-1234
    private String vehicleType;     // CAR / BIKE
    private Long locationId;        // optional (future use)
}
