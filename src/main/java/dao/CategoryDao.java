package dao;

import models.Booking;
import models.Category;

import java.util.List;

public interface CategoryDao {

    //LIST
    List<Booking> getAllBookingsByCategory(int categoryId);

    List<Category> getAll();


    //CREATE
    void add (Category category);

    //READ
    Category findById(int id);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllCategories();
}
