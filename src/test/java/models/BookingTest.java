package models;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BookingTest {
    @Test
    public void NewBookingObjectGetsCorrectlyCreated_true() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals(true, booking instanceof Booking);
    }

    @Test
    public void BookingInstantiatesWithDescription_true() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals("Mow the lawn", booking.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals(false, booking.isCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals(LocalDateTime.now().getDayOfWeek(), booking.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Booking setupNewBooking(){
        return new Booking("Mow the lawn", 1, 1);
    }

}
