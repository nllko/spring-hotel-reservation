package com.niko.hotelreservation.repositories;

import com.niko.hotelreservation.DTOs.PeriodDTO;
import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  @Query(
      "SELECT r.roomId FROM Reservation r "
          + "WHERE r.checkIn < :#{#dto.checkOut} AND r.checkOut > :#{#dto.checkIn}")
  Set<Long> findReservedRoomIds(
      @Param("dto") PeriodDTO dto);

  @Query(
      "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reservation r "
          + "WHERE r.roomId = :#{#dto.roomId} AND r.checkIn < :#{#dto.checkOut} AND r.checkOut > :#{#dto.checkIn}")
  boolean isRoomReserved(@Param("dto") ReservationDTO dto);
}
