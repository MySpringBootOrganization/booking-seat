package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.booking.controller.BookingSeatController;
import com.booking.model.Booking;
import com.booking.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.muserver.MuRequest;
import io.muserver.MuResponse;

class TestBookingSeatController {

    @Test
    void testHandle_ValidDateWithBookings() throws Exception {
        // Setup
        MuRequest request = Mockito.mock(MuRequest.class);
        MuResponse response = Mockito.mock(MuResponse.class);
        Map<String, String> pathParams = Map.of("date", "2025-04-02");

        LocalDate date = LocalDate.parse("2025-04-02");
        List<Booking> mockBookings = List.of(
                new Booking("John Doe", 4, "2025-04-02", 2),
                new Booking("Chis Smith", 8, "2025-04-02", 10));

        // Mock the repository
        try (MockedStatic<BookingRepository> mockedStatic = Mockito.mockStatic(BookingRepository.class)) {

            mockedStatic.when(() -> BookingRepository.getBookingsByDate(date)).thenReturn(mockBookings);

            // Execute
            BookingSeatController controller = new BookingSeatController();
            controller.handle(request, response, pathParams);
        }
        // Capture the JSON string written to the response
        ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(response).write(jsonCaptor.capture());

        // Parse JSON and verify size
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<?> bookingsList = mapper.readValue(jsonCaptor.getValue(), List.class);

        // Verify the JSON array has 2 elements (same as mockBookings)
        assertEquals(2, bookingsList.size());
    }

    @Test
    void testHandle_InvalidDateFormat() throws Exception {
        // Setup
        MuRequest request = Mockito.mock(MuRequest.class);
        MuResponse response = Mockito.mock(MuResponse.class);
        Map<String, String> pathParams = Map.of("date", "2023/10/15");

        BookingSeatController controller = new BookingSeatController();

        // Execute & Verify Exception
        Mockito.doThrow(new DateTimeParseException("Invalid date", "2023/10/15", 0))
                .when(response).write(Mockito.anyString());

        controller.handle(request, response, pathParams);

        // Verify error response
        Mockito.verify(response).status(400); // Assuming you set status
    }
}