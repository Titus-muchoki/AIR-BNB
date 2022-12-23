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
    public void isBookedPropertyIsFalseAfterInstantiation() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals(false, booking.isBooked()); //should never start as completed
    }

    @Test
    public void getStartDateInstantiatesWithCurrentTimeToday() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals("",booking.getStartDate());
    }
    @Test
    public void getEndDateInstantiatesWithSelectedDay() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals("", booking.getEndDate());
    }
    @Test
    public void getClientNameInstantiatesWithClientName() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals("", booking.getClientName());
    }
//    @Test
//    public void getTelInstantiatesWithClientTelNumber() throws Exception {
//        Booking booking = setupNewBooking();
//        assertEquals(1, booking.getTel());
//    }
    @Test
    public void getEmailInstantiatesWithClientEmail() throws Exception {
        Booking booking = setupNewBooking();
        assertEquals("", booking.getEmail());
    }
    //helper methods
    public Booking setupNewBooking(){
        return new Booking("Mow the lawn", "", "", "","",1);
    }

}
