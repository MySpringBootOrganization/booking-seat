package controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.controller.BookingController;
import com.booking.model.Booking;
import com.booking.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.muserver.MuRequest;
import io.muserver.MuResponse;

@ExtendWith(MockitoExtension.class)
class TestBookingController {

    @Mock
    private MuRequest mockRequest;
    
    @Mock
    private MuResponse mockResponse;
    
    @Mock
    private Map<String, String> mockPathParams;
    
    private BookingController bookingController;
    private ObjectMapper objectMapper;
    
    @BeforeEach
     void setUp() {
        bookingController = new BookingController();
        objectMapper = new ObjectMapper();
        BookingRepository.clearBookings(); // Assuming we can clear bookings between tests
    }

    @Test
     void testHandle_WithValidBooking_ShouldAddBookingAndReturnSuccess() throws Exception {
        // Arrange
        Booking expectedBooking = new Booking("John Doe",4,"2025-04-02",2);
         
        
        String bookingJson = objectMapper.writeValueAsString(expectedBooking);
        InputStream inputStream = new ByteArrayInputStream(bookingJson.getBytes());
        
        when(mockRequest.inputStream()).thenReturn(Optional.of(inputStream));
        
        // Act
        bookingController.handle(mockRequest, mockResponse, mockPathParams);
        
        // Assert
        verify(mockResponse).write("Booking successful");
        assertEquals(1, BookingRepository.getAllBookings().size());
        assertEquals(expectedBooking, BookingRepository.getAllBookings().get(0));
    }

    @Test
     void testHandle_WithEmptyInputStream_ShouldBeInvalid() throws Exception {
        // Arrange
        when(mockRequest.inputStream()).thenReturn(Optional.empty());
        
        // Act
        bookingController.handle(mockRequest, mockResponse, mockPathParams);
        
        // Assert
        verify(mockResponse).write("Invalid booking data");
        assertEquals(0, BookingRepository.getAllBookings().size());
    }


    
}