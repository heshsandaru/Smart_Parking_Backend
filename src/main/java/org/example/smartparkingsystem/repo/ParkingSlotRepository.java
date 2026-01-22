package org.example.smartparkingsystem.repo;

import org.example.smartparkingsystem.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    @Query("SELECT s FROM ParkingSlot s WHERE s.isAvailable = true AND s.slotType = :type")
    Optional<ParkingSlot> findFirstAvailableSlot(@Param("type" )String type);
}
