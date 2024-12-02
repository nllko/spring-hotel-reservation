package com.niko.hotelreservation.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.niko.hotelreservation.utils.LocalDateUtils.areDatesOverlapping;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LocalDateUtilsTest {
  private LocalDate startDate1;
  private LocalDate endDate1;
  private LocalDate startDate2;
  private LocalDate endDate2;

  /*
     Periods do not overlap
       Period1
     |---------|  Period2
               |----------|
               C          D
  */
  @Test
  void testPeriodsNotOverlap() {
    makeNotOverlappingPeriods();

    assertFalse(areDatesOverlapping(startDate1, endDate1, startDate2, endDate2));
  }

  /*
      Periods partially overlap
        Period1
      |---------|
           |----------|
              Period2
  */
  @Test
  void testPeriodsPartiallyOverlap() {
    makePartiallyOverlappingPeriods();

    assertTrue(areDatesOverlapping(startDate1, endDate1, startDate2, endDate2));
  }

  /*
      Periods fully overlap
        Period1
      |-------------|
        |---------|
          Period2
  */
  @Test
  void testPeriodsFullyOverlap() {
    makeFullyOverlappingPeriods();

    assertTrue(areDatesOverlapping(startDate1, endDate1, startDate2, endDate2));
  }

  private void makeNotOverlappingPeriods() {
    startDate1 = LocalDate.of(2024, 11, 1);
    endDate1 = LocalDate.of(2024, 11, 10);
    startDate2 = LocalDate.of(2024, 11, 10);
    endDate2 = LocalDate.of(2024, 11, 20);
  }

  private void makePartiallyOverlappingPeriods() {
    startDate1 = LocalDate.of(2024, 11, 1);
    endDate1 = LocalDate.of(2024, 11, 10);
    startDate2 = LocalDate.of(2024, 11, 5);
    endDate2 = LocalDate.of(2024, 11, 20);
  }

  private void makeFullyOverlappingPeriods() {
    startDate1 = LocalDate.of(2024, 11, 1);
    endDate1 = LocalDate.of(2024, 11, 30);
    startDate2 = LocalDate.of(2024, 11, 10);
    endDate2 = LocalDate.of(2024, 11, 20);
  }
}
