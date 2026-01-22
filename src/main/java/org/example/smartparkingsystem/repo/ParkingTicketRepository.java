package org.example.smartparkingsystem.repo;

import org.example.smartparkingsystem.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket,Long> {
    @Query("SELECT t FROM ParkingTicket t WHERE t.vehicle.vehicleNumber = :vn AND t.status='ACTIVE'")
    Optional<ParkingTicket> findActiveTicket(@Param("vn") String vehicleNumber);
}
