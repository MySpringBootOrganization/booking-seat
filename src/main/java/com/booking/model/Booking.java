package com.booking.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
   
    private String name;
    private Integer tableSize;
    private String bookingDate;
    private Integer slot;
     
 
}
