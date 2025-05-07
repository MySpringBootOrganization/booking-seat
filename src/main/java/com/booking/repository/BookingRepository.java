package com.booking.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.model.Booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingRepository {

    // Storage for bookings
    private static final List<Booking> allBookings = new ArrayList<>();
    private static final Map<LocalDate, List<Booking>> bookingsByDate = new HashMap<>();

    // Add a new booking
    public static void addBooking(Booking booking) {
        allBookings.add(booking);

        // Add to date index
        LocalDate date = LocalDate.parse(booking.getBookingDate());
        bookingsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(booking);
    }

    // Find bookings by exact date
    public static List<Booking> getBookingsByDate(LocalDate date) {
        return bookingsByDate.getOrDefault(date, new ArrayList<>());
    }

    // Get all bookings (optional)
    public static List<Booking> getAllBookings() {
        return new ArrayList<>(allBookings);
    }

    public static void clearBookings() {
        allBookings.clear();
        bookingsByDate.clear();
    }
    // Optional: Remove a booking by ID (if needed)
}
