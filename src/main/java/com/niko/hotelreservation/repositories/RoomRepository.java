package com.niko.hotelreservation.repositories;

import com.niko.hotelreservation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
