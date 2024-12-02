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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  List<Room> rooms = new ArrayList<>();
  List<Reservation> reservations = new ArrayList<>();
  LocalDate checkInDate = LocalDate.of(2024, 11, 1);
  LocalDate checkOutDate = LocalDate.of(2024, 11, 10);

  @InjectMocks private ReservationService reservationService;

  @Mock private RoomRepository roomRepository;

  @Mock private ReservationRepository reservationRepository;

  @BeforeEach
  void setUp() {
    createRooms();
    makeDates();
  }

  @Test
  void testGetAvailableRooms_NoReservations_ShouldBeNotEmpty() {
    List<Room> availableRooms = getAvailableRooms();

    assertFalse(availableRooms.isEmpty());
  }

  @Test
  void testGetAvailableRooms_ReservationPresentOnSameDate_ShouldNotContainRoomWithDate() {
    createReservation(0L, checkInDate, checkOutDate);
    List<Room> availableRooms = getAvailableRooms();

    assertFalse(availableRooms.isEmpty());
    assertFalse(availableRooms.stream().anyMatch(room -> room.getId() == 0L));
  }

  @Test
  void testGetAvailableRooms_ReservationPresentOnDifferentDate_ShouldContainRoomWithDifferentDates() {
    createReservation(0L, checkInDate.plusMonths(1), checkOutDate.plusMonths(1));
    List<Room> availableRooms = getAvailableRooms();

    assertTrue(availableRooms.stream().anyMatch(room -> room.getId() == 0L));
  }

  @Test
  void testGetAvailableRooms_AllRoomsReserved_ShouldReturnEmptyList() {
    createReservationsForAllRooms();

    List<Room> availableRooms = getAvailableRooms();

    assertTrue(availableRooms.isEmpty());
  }

  @Test
  public void testReserveRoom_NoReservations_ShouldReturnReservation() {
    ReservationDTO reservationDTO = createReservationDTO();

    when(reservationRepository.getAllByRoomId(reservationDTO.getRoomId())).thenReturn(Collections.emptyList());
    Reservation expectedReservation = new Reservation();
    when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

    Reservation result = reserveRoom(reservationDTO);

    assertNotNull(result);
    assertEquals(expectedReservation, result);
  }

  @Test
  void testReserveRoom_ReservationPresent_ShouldBeNull() {
    ReservationDTO reservationDTO = createReservationDTO();
    Reservation reservation = reservationDTO.toEntity();
    reservation.setId(0L);
    reservations.add(reservation);

    when(reservationRepository.getAllByRoomId(reservationDTO.getRoomId())).thenReturn(reservations);

    Reservation result = reserveRoom(reservationDTO);

    assertNull(result);
  }

  @Test
  void testCancelReservation_WhenReservationExist_ReturnReservation() {
    createReservation(0L, checkInDate, checkOutDate);
    reservationService.cancelReservation(0L);
    verify(reservationRepository).deleteById(0L);
  }

  private void createRooms() {
    for (int i = 0; i < 5; i++) {
      Room room = new Room();
      room.setId(i);
      rooms.add(room);
    }
  }

  private void makeDates() {
     checkInDate = LocalDate.of(2024, 11, 1);
     checkOutDate = LocalDate.of(2024, 11, 10);
  }

  private List<Room> getAvailableRooms() {
    when(roomRepository.findAll()).thenReturn(rooms);
    return reservationService.getAvailableRoomsByDates(checkInDate, checkOutDate);
  }

  private Reservation reserveRoom(ReservationDTO reservationDTO) {
    return reservationService.reserveRoomByDates(reservationDTO);
  }

  private ReservationDTO createReservationDTO() {
    ReservationDTO reservationDTO = new ReservationDTO();
    reservationDTO.setRoomId(0L);
    reservationDTO.setCheckInDate(checkInDate);
    reservationDTO.setCheckOutDate(checkOutDate);
    return reservationDTO;
  }

  private void createReservation(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
   // when(reservationRepository.findAll()).thenReturn(reservations);
    reservations.add(new Reservation(null, roomId, checkInDate, checkOutDate));
  }

  private void createReservationsForAllRooms() {
    for (Room room : rooms) {
      createReservation(room.getId(), checkInDate, checkOutDate);
    }
  }
}
