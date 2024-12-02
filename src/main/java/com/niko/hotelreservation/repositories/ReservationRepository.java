package com.niko.hotelreservation.repositories;

import com.niko.hotelreservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getAllByRoomId(Long roomId);
}
