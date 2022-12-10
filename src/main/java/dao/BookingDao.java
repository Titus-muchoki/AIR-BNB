package dao;

import models.Booking;

import java.util.List;

public interface BookingDao {
    // LIST
    List<Booking> getAll();

    // CREATE
    void add(Booking booking);

    // READ
    Booking findById(int id);

    // UPDATE
    void update(int id, String content, int categoryId);

    // DELETE
    void deleteById(int id);
    void clearAllBookings();
}
