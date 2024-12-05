package com.niko.hotelreservation.DTOs;

import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.validation.validPeriod.ValidPeriod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

import static com.niko.hotelreservation.constants.Messages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class ReservationDTO {
  @NotNull(message = VALIDATION_ID_EMPTY)
  @Min(value = 0L, message = VALIDATION_ID_NEGATIVE)
  private Long roomId;

  @NotNull(message = VALIDATION_PERIOD_EMPTY)
  @ValidPeriod
  private PeriodDTO period;

  public Reservation toEntity() {
    Reservation reservation = new Reservation();
    reservation.setRoomId(roomId);
    reservation.setCheckIn(period.getCheckIn());
    reservation.setCheckOut(period.getCheckOut());
    return reservation;
  }

  public LocalDate getCheckIn() {
    return period.getCheckIn();
  }

  public LocalDate getCheckOut() {
    return period.getCheckOut();
  }
}
