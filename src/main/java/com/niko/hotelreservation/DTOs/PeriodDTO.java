package com.niko.hotelreservation.DTOs;

import jakarta.validation.constraints.Future;
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
public class PeriodDTO {
  @NotNull(message = "{validation.reservationDto.dates.empty}")
  @Future(message = "{validation.reservationDto.dates.past}")
  private LocalDate checkIn;

  @NotNull(message = "{validation.reservationDto.dates.empty}")
  @Future(message = "{validation.reservationDto.dates.past}")
  private LocalDate checkOut;
}
