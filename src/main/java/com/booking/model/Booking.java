package com.booking.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Booking {
   
    private String name;
    private int tableSize;
    private String bookingDate;
    private int slot;
     
 
}
