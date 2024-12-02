package com.niko.hotelreservation.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niko.hotelreservation.entities.Reservation;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class ReservationDTO {
  private Long roomId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate checkInDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate checkOutDate;

  public Reservation toEntity() {
    Reservation reservation = new Reservation();
    reservation.setRoomId(roomId);
    reservation.setCheckIn(checkInDate);
    reservation.setCheckOut(checkOutDate);
    return reservation;
  }
}
