package org.example.smartparkingsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.smartparkingsystem.entity.ParkingSlot;
import org.example.smartparkingsystem.entity.ParkingTicket;
import org.example.smartparkingsystem.repo.ParkingSlotRepository;
import org.example.smartparkingsystem.repo.ParkingTicketRepository;
import org.example.smartparkingsystem.repo.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingService {
    private final VehicleRepository vehicleRepo;
    private final ParkingSlotRepository slotRepo;
    private final ParkingTicketRepository ticketRepo;

    private static final double CAR_RATE = 100;
    private static final double BIKE_RATE = 50;


    public ParkingTicket enterVehicle(EntryRequestDTO dto) {

        ticketRepo.findActiveTicket(dto.getVehicleNumber())
                .ifPresent(t -> { throw new RuntimeException("Already parked"); });

        ParkingSlot slot = slotRepo
                .findFirstAvailableSlot(dto.getVehicleType())
                .orElseThrow(() -> new SlotNotAvailableException("No slot"));

        Vehicle vehicle = vehicleRepo.findByVehicleNumber(dto.getVehicleNumber())
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setVehicleNumber(dto.getVehicleNumber());
                    v.setVehicleType(dto.getVehicleType());
                    return vehicleRepo.save(v);
                });

        slot.setAvailable(false);

        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setSlot(slot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus("ACTIVE");

        slotRepo.save(slot);
        return ticketRepo.save(ticket);
    }

    @Override
    public ExitResponseDTO exitVehicle(Long id) {

        ParkingTicket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setExitTime(LocalDateTime.now());
        ticket.setStatus("COMPLETED");

        long hours = Math.max(1,
                Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours());

        double rate = ticket.getVehicle().getVehicleType().equals("CAR") ? CAR_RATE : BIKE_RATE;
        double amount = hours * rate;

        ticket.getSlot().setAvailable(true);

        return ExitResponseDTO.builder()
                .ticketId(ticket.getId())
                .vehicleNumber(ticket.getVehicle().getVehicleNumber())
                .amount(amount)
                .paymentStatus("UNPAID")
                .build();
    }
}
