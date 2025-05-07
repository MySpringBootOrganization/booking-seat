package com.booking.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.booking.model.Booking;
import com.booking.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

public class BookingSeatController implements RouteHandler {
    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
        // Handle the booking seat logic here
        String date = pathParams.get("date");
        LocalDate bookingDate = LocalDate.parse(date);
        List<Booking> bookingList = BookingRepository.getBookingsByDate(bookingDate);
        ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); 
        
        String json = mapper.writeValueAsString(bookingList);
        response.write(json);
    }
}
