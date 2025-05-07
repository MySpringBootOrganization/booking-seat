package com.booking.controller;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import com.booking.model.Booking;
import com.booking.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

public class BookingController implements RouteHandler {
    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        
        Optional<InputStream> inputStreamOption = request.inputStream();
        if (inputStreamOption.isPresent()) {
            InputStream inputStream = inputStreamOption.get();
            Booking newBooking = new Booking();
            newBooking = objectMapper.readValue(inputStream, Booking.class);
            BookingRepository.addBooking(newBooking);
        } else {
            response.status(400);
            response.write("Invalid booking data");
            return;
        }

       

        response.write("Booking successful");
    }

}
