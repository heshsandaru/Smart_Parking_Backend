package org.example.smartparkingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExitResponseDTO {
    private Long ticketId;
    private String vehicleNumber;
    private double amount;
    private String paymentStatus;   // PAID / UNPAID
}
