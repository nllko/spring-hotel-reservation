package com.niko.hotelreservation.DTOs;

import com.niko.hotelreservation.entities.Reservation;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
  @NotNull(message = "{validation.reservationDto.id.empty}")
  @Min(value = 0L, message = "{validation.reservationDto.id.min}")
  private Long roomId;

  @NotNull(message = "{validation.reservationDto.dates.empty}")
  @Future(message = "{validation.reservationDto.dates.past}")
  private LocalDate checkIn;

  @NotNull(message = "{validation.reservationDto.dates.empty}")
  @Future(message = "{validation.reservationDto.dates.past}")
  private LocalDate checkOut;

  public Reservation toEntity() {
    Reservation reservation = new Reservation();
    reservation.setRoomId(roomId);
    reservation.setCheckIn(checkIn);
    reservation.setCheckOut(checkOut);
    return reservation;
  }
}
