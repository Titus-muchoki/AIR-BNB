package dao;

import models.Booking;
import models.BookingTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Sql2oBookingDaoTest {
    private Sql2oBookingDao bookingDao; //ignore me for now. We'll create this soon.
    private static Connection con; //must be sql2o class conn

    @Before
    public void setUp() {
        String connectionString = "jdbc:postgresql://localhost:5432/airbnb_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        bookingDao = new Sql2oBookingDao(sql2o); //ignore me for now
        con = sql2o.open(); //keep connection open through entire test so, it does not get erased
    }

    @After
    public void tearDown() {
        System.out.println("clearing database");
        bookingDao.clearAllBookings();
        con.close();
    }
    @AfterClass
    public static void shutDown() {
        con.close();
        System.out.println("connection closed");
    }
    @Test
    public void addingBookingSetsId() {
        Booking booking = setupNewBooking();
        int originalBookingId = booking.getId();
        bookingDao.add(booking);
        assertNotEquals(originalBookingId, booking.getId()); //how does this work?
    }
    @Test
    public void existingBookingsCanBeFoundById() {
        Booking booking = setupNewBooking();
        bookingDao.add(booking); //add to dao (takes care of saving)
        Booking foundBooking = bookingDao.findById(booking.getId()); //retrieve
        assertNotEquals(booking, foundBooking); //should be the same
    }

    @Test
    public void addedBookingsAreReturnedFromGetAll() {
        Booking booking = setupNewBooking();
        bookingDao.add(booking);
        assertEquals(1, bookingDao.getAll().size());
    }

    @Test
    public void noBookingsReturnsEmptyList() {
        assertEquals(0, bookingDao.getAll().size());
    }


    @Test
    public void updateChangesBookingContent() {
        String initialDescription = "mow the lawn";
        Booking booking = new Booking (initialDescription, 1,1);// or use the helper method for easier refactoring
        bookingDao.add(booking);
        bookingDao.update(booking.getId(),"brush the cat", 1,1);
        Booking updatedBooking =  bookingDao.findById(booking.getId()); //why do I need to refind this?
        assertEquals(initialDescription, updatedBooking.getDescription());
    }
    @Test
    public void deleteByIdDeletesCorrectLoan() {
        Booking booking = setupNewBooking();
        bookingDao.add( booking);
        bookingDao.deleteById(booking.getId());
        assertEquals(0,  bookingDao.getAll().size());
    }
    @Test
    public void clearAllClearsAll() {
        Booking booking = setupNewBooking();
        Booking otherBooking = new Booking("brush the cat", 1,1);
        bookingDao.add(booking);
        bookingDao.add(otherBooking);
        int daoSize = bookingDao.getAll().size();
        bookingDao.clearAllBookings();
        assertTrue(daoSize > 0 && daoSize > bookingDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }
    @Test
    public void categoryIdIsReturnedCorrectly() {
        Booking booking = setupNewBooking();
        int originalCatId = booking.getCategoryId();
        bookingDao.add(booking);
        assertEquals(originalCatId, bookingDao.findById(booking.getId()).getCategoryId());
    }
    //define the following once and then call it as above in your tests.
    public Booking setupNewBooking(){
        return new Booking("Mow the lawn", 1,1);
    }

}
