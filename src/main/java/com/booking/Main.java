package com.booking;

import  com.booking.controller.BookingController;
import com.booking.controller.BookingSeatController;

import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;

public class Main {
    public static void main(String[] args) {
          MuServer server = MuServerBuilder.httpServer()
            .addHandler(Method.GET, "/hello", (request, response, pathParams) -> {
                response.write("Hello, world");
            })
            .addHandler(Method.POST,"/newbooking",new BookingController())
            .addHandler(Method.GET, "/bookingdate/{date}", new BookingSeatController())
            .withHttpPort(8080)
            .start();
        System.out.println("Started server at " + server.uri());
    }

}