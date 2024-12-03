package com.niko.hotelreservation.services;

import com.niko.hotelreservation.DTOs.ReservationDTO;
import com.niko.hotelreservation.entities.Reservation;
import com.niko.hotelreservation.entities.Room;
import com.niko.hotelreservation.repositories.ReservationRepository;
import com.niko.hotelreservation.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  private List<Room> allRooms;
  private List<Reservation> allReservations;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private ReservationDTO reservationDTO;

  @InjectMocks private ReservationService reservationService;

  @Mock private RoomRepository roomRepository;

  @Mock private ReservationRepository reservationRepository;

  @BeforeEach
  void setUp() {
    allRooms = createRooms(5);
    allReservations = new ArrayList<>();
    setDates(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10));
  }

  @Test
  void testGetAvailableRooms_NoReservations_ShouldBeNotEmpty() {
    mockRooms(allRooms);
    mockReservations(allReservations);

    List<Room> availableRooms = getAvailableRooms();

    assertFalse(availableRooms.isEmpty());
  }

  @Test
  void testGetAvailableRooms_ReservationPresentOnSameDate_ShouldNotContainReservedRoom() {
    mockRooms(allRooms);
    addReservation(0L, checkIn, checkOut);
    mockReservations(allReservations);

    List<Room> availableRooms = getAvailableRooms();

    assertFalse(containsRoomWithId(availableRooms, 0L));
  }

  @Test
  void
      testGetAvailableRooms_ReservationPresentOnDifferentDate_ShouldContainRoomWithDifferentDates() {
    mockRooms(allRooms);
    addReservation(0L, checkIn.plusMonths(1), checkOut.plusMonths(1));
    mockReservations(allReservations);

    List<Room> availableRooms = getAvailableRooms();

    assertTrue(containsRoomWithId(availableRooms, 0L));
  }

  @Test
  void testGetAvailableRooms_AllRoomsReserved_ShouldReturnEmptyList() {
    mockRooms(allRooms);
    addReservationsForAllRooms(checkIn, checkOut);
    mockReservations(allReservations);

    List<Room> availableRooms = getAvailableRooms();

    assertTrue(availableRooms.isEmpty());
  }

  @Test
  void testReserveRoom_NoReservations_ShouldReturnReservation() {
    makeReservationDTO(0L, checkIn, checkOut);
    Reservation expectedReservation = new Reservation(0L, 0L, checkIn, checkOut);
    when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

    Reservation reservation = reserveRoom();

    assertEquals(expectedReservation, reservation);
  }

  @Test
  void testReserveRoom_ReservationPresent_ShouldReturnNull() {
    addReservation(0L, checkIn, checkOut);
    mockReservations(allReservations);
    makeReservationDTO(0L, checkIn, checkOut);

    Reservation reservation = reserveRoom();

    assertNull(reservation);
  }

  private List<Room> createRooms(int count) {
    List<Room> rooms = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Room room = new Room();
      room.setId(i);
      rooms.add(room);
    }
    return rooms;
  }

  private void setDates(LocalDate checkIn, LocalDate checkOut) {
    this.checkIn = checkIn;
    this.checkOut = checkOut;
  }

  private void makeReservationDTO(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    reservationDTO = new ReservationDTO(roomId, checkIn, checkOut);
  }

  private void addReservation(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    allReservations.add(new Reservation(0L, roomId, checkIn, checkOut));
  }

  private void addReservationsForAllRooms(LocalDate checkIn, LocalDate checkOut) {
    for (Room room : allRooms) {
      allReservations.add(new Reservation(null, room.getId(), checkIn, checkOut));
    }
  }

  private void mockRooms(List<Room> rooms) {
    when(roomRepository.findAll()).thenReturn(rooms);
  }

  private void mockReservations(List<Reservation> reservations) {
    when(reservationRepository.findAll()).thenReturn(reservations);
  }

  private List<Room> getAvailableRooms() {
    return reservationService.getAvailableRoomsByDates(checkIn, checkOut);
  }

  private Reservation reserveRoom() {
    return reservationService.reserveRoomByDates(reservationDTO);
  }

  private boolean containsRoomWithId(List<Room> rooms, Long roomId) {
    return rooms.stream().anyMatch(room -> room.getId() == roomId);
  }
}
