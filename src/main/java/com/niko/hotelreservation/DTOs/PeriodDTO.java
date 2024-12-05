package com.niko.hotelreservation.DTOs;

import com.niko.hotelreservation.validation.validPeriod.ValidPeriod;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import static com.niko.hotelreservation.constants.Messages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPeriod
public class PeriodDTO {
  @NotNull(message = VALIDATION_CHECKIN_EMPTY)
  @Future(message = VALIDATION_CHECKIN_PAST)
  private LocalDate checkIn;

  @NotNull(message = VALIDATION_CHECKOUT_EMPTY)
  @Future(message = VALIDATION_CHECKOUT_PAST)
  private LocalDate checkOut;
}
