package com.urielsmp.lil.learningspring.util;

import com.urielsmp.lil.learningspring.business.ReservationService;
import com.urielsmp.lil.learningspring.business.RoomReservation;
import com.urielsmp.lil.learningspring.data.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AppStartUpEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final ReservationService reservationService;
    private final DateUtils dateUtils;

    public AppStartUpEvent(ReservationService reservationService, DateUtils dateUtils) {
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;
    }



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Date date = this.dateUtils.createDateFromDateString("2022-01-01");
        List<RoomReservation> reservations = this.reservationService.getRoomReservationsForDate(date);
        reservations.forEach(System.out::println);

    }
}
