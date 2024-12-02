package com.niko.hotelreservation.utils;

import java.time.LocalDate;

public class LocalDateUtils {
  public static boolean areDatesOverlapping(
      LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
    return start1.isBefore(end2) && start2.isBefore(end1);
  }
}
