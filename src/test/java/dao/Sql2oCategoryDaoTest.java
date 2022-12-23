package dao;

import models.Booking;
import models.Category;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class Sql2oCategoryDaoTest {
    private Sql2oCategoryDao categoryDao; //ignore me for now. We'll create this soon.
    private Sql2oBookingDao bookingDao;
    private static Connection con; //must be sql2o class conn


    @Before
    public void setUp() {
        String connectionString = "jdbc:postgresql://localhost:5432/airbnb_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        categoryDao = new Sql2oCategoryDao(sql2o); //ignore me for now
        bookingDao = new Sql2oBookingDao(sql2o);
        con = sql2o.open(); //keep connection open through entire test so, it does not get erased
    }

    @After
    public void tearDown() {
        System.out.println("clearing database");
        categoryDao.clearAllCategories();           // clear all categories after every test
        bookingDao.clearAllBookings();
        con.close();
    }
    @AfterClass                                     //run once after all tests in this file completed
    public static void shutDown() {
        con.close();                               // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }
    @Test
    public void addingCategorySetsId() {
        Category category = new Category(1);
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId()); //how does this work?

    }

    @Test
    public void existingCategoryCanBeFoundById() {
        Category category = new Category(1);
        categoryDao.add(category);
        Category foundCategory = categoryDao.findById(category.getId());
        assertEquals(category, foundCategory); //should be the same

    }
    @Test
    public void addedCategoriesAreReturnedFromGetAll() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        assertEquals(1, categoryDao.getAll().size());
    }
    @Test
    public void noCategoriesReturnsEmptyList() {
        assertEquals(0, categoryDao.getAll().size());
    }
    @Test
    public void updateChangesCategoryContent() {
        int initialDescription = 1;
        Category category = new Category (initialDescription);
        categoryDao.add(category);
        categoryDao.update(category.getId(),2);
        Category updatedCategory = categoryDao.findById(category.getId());
        assertNotEquals(initialDescription, updatedCategory.getAmount());
    }
    @Test
    public void deleteByIdDeletesCorrectCategory() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        categoryDao.deleteById(category.getId());
        assertEquals(0, categoryDao.getAll().size());
    }
    @Test
    public void clearAllClearsAllCategories() {
        Category category = setupNewCategory();
        Category otherCategory = new Category(1);
        categoryDao.add(category);
        categoryDao.add(otherCategory);
        int daoSize = categoryDao.getAll().size();
        categoryDao.clearAllCategories();
        assertTrue(daoSize > 0 && daoSize > categoryDao.getAll().size());
    }
    @Test
    public void getAllBookingByCategoryReturnsBookingsCorrectly() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Booking newBooking = new Booking("mow the lawn", "" , "", "","",categoryId);
        Booking otherBooking = new Booking("pull weeds", "","", "", "", categoryId);
        Booking thirdBooking = new Booking("trim hedge","","", "", "",  categoryId);
        bookingDao.add(newBooking);
        bookingDao.add(otherBooking); //we are not adding loan 3 so, we can test things precisely.
        assertEquals(2, categoryDao.getAllBookingsByCategory( categoryId).size());
        assertTrue(categoryDao.getAllBookingsByCategory( categoryId).contains(newBooking));
        assertTrue(categoryDao.getAllBookingsByCategory( categoryId).contains(otherBooking));
        assertFalse(categoryDao.getAllBookingsByCategory( categoryId).contains(thirdBooking)); //things are accurate!
    }
    public Category setupNewCategory(){
        return new Category(1);
    }

}
