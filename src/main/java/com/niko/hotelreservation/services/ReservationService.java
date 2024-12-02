package com.niko.hotelreservation.services;

import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.entities.Room;
import com.niko.hotelreservation.repositories.ReservationRepository;
import com.niko.hotelreservation.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.niko.hotelreservation.utils.LocalDateUtils.areDatesOverlapping;

@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final RoomRepository roomRepository;

  public ReservationService(
      ReservationRepository reservationRepository, RoomRepository roomRepository) {
    this.reservationRepository = reservationRepository;
    this.roomRepository = roomRepository;
  }

  public List<Room> getAvailableRoomsByDates(LocalDate checkIn, LocalDate checkOut) {
    Set<Long> reservedRoomIds = getReservedRoomIds(checkIn, checkOut);
    return roomRepository.findAll().stream()
        .filter(room -> !reservedRoomIds.contains(room.getId()))
        .collect(Collectors.toList());
  }

  public Reservation reserveRoomByDates(ReservationDTO reservationDTO) {
    if (isRoomAvailable(
        reservationDTO.getRoomId(),
        reservationDTO.getCheckInDate(),
        reservationDTO.getCheckOutDate())) {
      return reservationRepository.save(reservationDTO.toEntity());
    }
    return null;
  }

  public void cancelReservation(Long reservationId) {
    reservationRepository.deleteById(reservationId);
  }

  private boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    return reservationRepository.findAll().stream()
        .filter(reservation -> reservation.getRoomId().equals(roomId))
        .noneMatch(
            reservation ->
                areDatesOverlapping(
                    reservation.getCheckIn(), reservation.getCheckOut(), checkIn, checkOut));
  }

  private Set<Long> getReservedRoomIds(LocalDate checkIn, LocalDate checkOut) {
    return reservationRepository.findAll().stream()
        .filter(
            reservation ->
                areDatesOverlapping(
                    reservation.getCheckIn(), reservation.getCheckOut(), checkIn, checkOut))
        .map(Reservation::getRoomId)
        .collect(Collectors.toSet());
  }
}
