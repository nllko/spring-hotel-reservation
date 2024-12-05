package com.niko.hotelreservation.services;

import com.niko.hotelreservation.DTOs.PeriodDTO;
import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.entities.Room;
import com.niko.hotelreservation.repositories.ReservationRepository;
import com.niko.hotelreservation.repositories.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static com.niko.hotelreservation.constants.Messages.*;

@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final RoomRepository roomRepository;

  public ReservationService(
      ReservationRepository reservationRepository, RoomRepository roomRepository) {
    this.reservationRepository = reservationRepository;
    this.roomRepository = roomRepository;
  }

  public List<Room> getAvailableRooms(PeriodDTO periodDTO) {
    Set<Long> reservedRoomIds = reservationRepository.findReservedRoomIds(periodDTO);
    return roomRepository.findAll().stream()
        .filter(room -> !reservedRoomIds.contains(room.getId()))
        .toList();
  }

  public Reservation reserveRoom(ReservationDTO reservationDTO) {
    if (!roomRepository.existsById(reservationDTO.getRoomId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, ROOM_DOES_NOT_EXIST);
    }
    boolean isRoomAvailable = !reservationRepository.isRoomReserved(reservationDTO);
    if (!isRoomAvailable) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, RESERVATION_ALREADY_EXISTS);
    }
    return reservationRepository.save(reservationDTO.toEntity());
  }

  public void cancelReservation(Long reservationId) {
    if (!reservationRepository.existsById(reservationId)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, RESERVATION_DOES_NOT_EXIST);
    }
    reservationRepository.deleteById(reservationId);
  }
}
