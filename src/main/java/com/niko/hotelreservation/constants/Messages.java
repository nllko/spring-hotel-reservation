package com.niko.hotelreservation.constants;

public class Messages {
  public static final String RESERVATION_ALREADY_EXISTS = "Reservation with this room and dates already exists";
  public static final String RESERVATION_DELETE_SUCCESS = "Reservation successfully deleted";
  public static final String RESERVATION_DELETE_DOES_NOT_EXIST = "Reservation does not exist";

  public static final String VALIDATION_ID_EMPTY = "Id can't be null";
  public static final String VALIDATION_ID_NEGATIVE = "Id can't be negative";
  public static final String VALIDATION_PERIOD_EMPTY = "Period can't be null";
  public static final String VALIDATION_CHECKIN_EMPTY = "Check-in can't be null";
  public static final String VALIDATION_CHECKOUT_EMPTY = "Check-in can't be null";
  public static final String VALIDATION_CHECKIN_PAST = "Check-in can't be in the past";
  public static final String VALIDATION_CHECKOUT_PAST = "Check-out can't be in the past";

  public static final String VALIDATION_PERIOD_INVALID = "Check-out can't be before check-in!";
}
