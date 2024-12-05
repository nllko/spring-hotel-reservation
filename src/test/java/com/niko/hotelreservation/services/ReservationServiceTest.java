package com.niko.hotelreservation.services;

import com.niko.hotelreservation.DTOs.PeriodDTO;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.niko.hotelreservation.utils.LocalDateUtils.areDatesOverlapping;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  private List<Room> allRooms;
  private List<Reservation> allReservations;
  private PeriodDTO period;
  private ReservationDTO reservationDTO;

  @InjectMocks private ReservationService reservationService;

  @Mock private RoomRepository roomRepository;

  @Mock private ReservationRepository reservationRepository;

  @BeforeEach
  void setUp() {
    allRooms = createRooms(5);
    allReservations = new ArrayList<>();
    setPeriod(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10));
  }

  @Test
  void testGetAvailableRooms_NoReservations_ShouldBeNotEmpty() {
    mockRooms(allRooms);
    mockReservedRoomIds();

    List<Room> availableRooms = getAvailableRooms();

    assertFalse(availableRooms.isEmpty());
  }

  @Test
  void testGetAvailableRooms_ReservationPresentOnSameDate_ShouldNotContainReservedRoom() {
    mockRooms(allRooms);
    addReservation(0L, period);
    mockReservedRoomIds();

    List<Room> availableRooms = getAvailableRooms();

    assertFalse(containsRoomWithId(availableRooms, 0L));
  }

  @Test
  void
      testGetAvailableRooms_ReservationPresentOnDifferentDate_ShouldContainRoomWithDifferentDates() {
    mockRooms(allRooms);
    addReservation(
        0L, new PeriodDTO(period.getCheckIn().plusMonths(1), period.getCheckOut().plusMonths(1)));
    mockReservedRoomIds();

    List<Room> availableRooms = getAvailableRooms();

    assertTrue(containsRoomWithId(availableRooms, 0L));
  }

  @Test
  void testGetAvailableRooms_AllRoomsReserved_ShouldReturnEmptyList() {
    mockRooms(allRooms);
    addReservationsForAllRooms(period);
    mockReservedRoomIds();

    List<Room> availableRooms = getAvailableRooms();

    assertTrue(availableRooms.isEmpty());
  }

  @Test
  void testReserveRoom_NoReservations_ShouldReturnReservation() {
    mockIsRoomReserved(false);
    mockRoomExistsById(true);
    makeReservationDTO(0L, period);
    Reservation expectedReservation =
        new Reservation(0L, 0L, period.getCheckIn(), period.getCheckOut());
    when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

    Reservation reservation = reserveRoom();

    assertEquals(expectedReservation, reservation);
  }

  @Test
  void testReserveRoom_ReservationPresent_ShouldReturnException() {
    makeReservationDTO(0L, period);

    assertThrows(ResponseStatusException.class, this::reserveRoom);
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

  private void setPeriod(LocalDate checkIn, LocalDate checkOut) {
    this.period = new PeriodDTO(checkIn, checkOut);
  }

  private void makeReservationDTO(Long roomId, PeriodDTO period) {
    reservationDTO = new ReservationDTO(roomId, period);
  }

  private void addReservation(Long roomId, PeriodDTO period) {
    allReservations.add(new Reservation(0L, roomId, period.getCheckIn(), period.getCheckOut()));
  }

  private void addReservationsForAllRooms(PeriodDTO period) {
    for (Room room : allRooms) {
      allReservations.add(
          new Reservation(null, room.getId(), period.getCheckIn(), period.getCheckOut()));
    }
  }

  private void mockRooms(List<Room> rooms) {
    when(roomRepository.findAll()).thenReturn(rooms);
  }

  private void mockReservedRoomIds() {
    Set<Long> reservedIds =
        allReservations.stream()
            .filter(
                reservation ->
                    areDatesOverlapping(
                        period.getCheckIn(),
                        period.getCheckOut(),
                        reservation.getCheckIn(),
                        reservation.getCheckOut()))
            .map(Reservation::getRoomId)
            .collect(Collectors.toSet());
    when(reservationRepository.findReservedRoomIds(period)).thenReturn(reservedIds);
  }

  private void mockIsRoomReserved(boolean reserved) {
    when(reservationRepository.isRoomReserved(any(ReservationDTO.class))).thenReturn(reserved);
  }

  private void mockRoomExistsById(boolean exists) {
    when(roomRepository.existsById(anyLong())).thenReturn(exists);
  }

  private List<Room> getAvailableRooms() {
    return reservationService.getAvailableRooms(period);
  }

  private Reservation reserveRoom() {
    return reservationService.reserveRoom(reservationDTO);
  }

  private boolean containsRoomWithId(List<Room> rooms, Long roomId) {
    return rooms.stream().anyMatch(room -> room.getId() == roomId);
  }
}
