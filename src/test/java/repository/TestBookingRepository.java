package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.booking.model.Booking;
import com.booking.repository.BookingRepository;

class TestBookingRepository {
    private Booking testBooking1;
    private Booking testBooking2;
    private Booking testBookingSameDate;

    @BeforeEach
    void setUp() {
        // Clear repository before each test
        BookingRepository.clearBookings();

        // Create test bookings
        testBooking1 = new Booking("John Doe", 8, "2025-04-12", 1);
        testBooking2 = new Booking("Jane Smith", 2, "2025-04-02", 5);
        testBookingSameDate = new Booking("Francis Lee", 5, "2025-04-12", 3);
    }

    @AfterEach
    void tearDown() {
        BookingRepository.clearBookings();
    }

    @Test
    void addBooking_shouldStoreBookingInAllBookings() {
        BookingRepository.addBooking(testBooking1);
        List<Booking> allBookings = BookingRepository.getAllBookings();

        assertEquals(1, allBookings.size());
        assertEquals(testBooking1, allBookings.get(0));
    }

    @Test
    void addBooking_shouldIndexBookingByDate() {
        BookingRepository.addBooking(testBooking1);
        LocalDate date = LocalDate.parse(testBooking1.getBookingDate());

        List<Booking> bookings = BookingRepository.getBookingsByDate(date);
        assertEquals(1, bookings.size());
        assertEquals(testBooking1, bookings.get(0));
    }

    @Test
    void addBooking_multipleBookingsSameDate_shouldGroupCorrectly() {
        BookingRepository.addBooking(testBooking1);
        BookingRepository.addBooking(testBookingSameDate);
        LocalDate date = LocalDate.parse(testBooking1.getBookingDate());

        List<Booking> bookings = BookingRepository.getBookingsByDate(date);
        assertEquals(2, bookings.size());
        assertTrue(bookings.contains(testBooking1));
        assertTrue(bookings.contains(testBookingSameDate));
    }

    @Test
    void getBookingsByDate_withExistingBookings_shouldReturnCorrectBookings() {
        BookingRepository.addBooking(testBooking1);
        BookingRepository.addBooking(testBooking2);

        LocalDate date1 = LocalDate.parse(testBooking1.getBookingDate());
        List<Booking> bookings1 = BookingRepository.getBookingsByDate(date1);
        assertEquals(1, bookings1.size());
        assertEquals(testBooking1, bookings1.get(0));

        LocalDate date2 = LocalDate.parse(testBooking2.getBookingDate());
        List<Booking> bookings2 = BookingRepository.getBookingsByDate(date2);
        assertEquals(1, bookings2.size());
        assertEquals(testBooking2, bookings2.get(0));
    }

    @Test
    void getBookingsByDate_withNonExistentDate_shouldReturnEmptyList() {
        BookingRepository.addBooking(testBooking1);
        LocalDate nonExistentDate = LocalDate.parse("2023-12-31");

        List<Booking> bookings = BookingRepository.getBookingsByDate(nonExistentDate);
        assertTrue(bookings.isEmpty());
    }

}
