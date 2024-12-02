package com.niko.hotelreservation.controllers;

import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.entities.Room;
import com.niko.hotelreservation.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {
    private final ReservationService reservationService;

    public HotelController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/available-rooms")
    public List<Room> getAvailableRoomsByDates(@RequestBody LocalDate checkIn, @RequestBody LocalDate checkOut) {
        return reservationService.getAvailableRoomsByDates(checkIn, checkOut);
    }

    @PostMapping("/reserve")
    public Reservation reserveRoomByDates(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.reserveRoomByDates(reservationDTO);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}
