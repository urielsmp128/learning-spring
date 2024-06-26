package com.urielsmp.lil.learningspring.business;

import com.urielsmp.lil.learningspring.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {


    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository){
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date){
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });

        Iterable<Reservation> reservations = this.reservationRepository.findByRsvDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation;
            roomReservation = roomReservationMap.get(reservation.getRoomId());
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getId());
        });

        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long id : roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(id));
        }

        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;

    }

    public List<Guest> getHotelGuests(){

        Iterable<Guest> guests = this.guestRepository.findAll();
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guest -> guestList.add(guest));
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if (o1.getLastName().equals(o2.getLastName())){
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public void addGuest(Guest guest){
        if(guest == null){
            throw new RuntimeException("Guest cannot be null");
        }
        this.guestRepository.save(guest);
    }

    public List<Room> getRooms(){
        Iterable<Room> rooms = this.roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();
        rooms.forEach(room -> roomList.add(room));
        roomList.sort(new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            }
        });
        return roomList;
    }
}
