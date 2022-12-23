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
    void update(int id, String description, String startDate, String endDate, String clientName,  String email, int categoryId);

    // DELETE
    void deleteById(int id);
    void clearAllBookings();
}
