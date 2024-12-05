package com.niko.hotelreservation.controllers;

import com.niko.hotelreservation.DTOs.PeriodDTO;
import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.entities.Room;
import com.niko.hotelreservation.constants.Messages;
import com.niko.hotelreservation.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {
  private final ReservationService reservationService;

  public HotelController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/available-rooms")
  public ResponseEntity<List<Room>> getAvailableRooms(@Valid @RequestBody PeriodDTO periodDTO) {
    List<Room> availableRooms = reservationService.getAvailableRooms(periodDTO);
    return ResponseEntity.status(HttpStatus.OK).body(availableRooms);
  }

  @PostMapping("/reserve")
  public ResponseEntity<Reservation> reserveRoom(
      @Valid @RequestBody ReservationDTO reservationDTO) {
    Reservation reservation = reservationService.reserveRoom(reservationDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
  }

  @DeleteMapping("/cancel/{id}")
  public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
    reservationService.cancelReservation(id);
    return ResponseEntity.status(HttpStatus.OK).body(Messages.RESERVATION_DELETE_SUCCESS);
  }
}
