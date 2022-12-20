import dao.Sql2oBookingDao;
import dao.Sql2oCategoryDao;
import models.Booking;
import models.Category;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
//        port(8090);
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/airbnb";      //connect to todolist, not todolist_test!

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        Sql2oBookingDao bookingDao = new Sql2oBookingDao(sql2o);
        Sql2oCategoryDao categoryDao = new Sql2oCategoryDao(sql2o);


        //get: show all bookings in all categories and show all categories

        get("/mybookings", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            model = new HashMap<>();
            List<Booking> bookings = bookingDao.getAll();
            model.put("bookings", bookings);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category

        get("/categories/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> categories = categoryDao.getAll(); //refresh list of links for navbar
            model.put("categories", categories);
            return new ModelAndView(model, "category-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category

        post("/categories", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Category newCategory = new Category(name);
            categoryDao.add(newCategory);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all bookings

        get("/categories/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            categoryDao.clearAllCategories();
            bookingDao.clearAllBookings();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all bookings

        get("/bookings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            bookingDao.clearAllBookings();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific category (and the bookings it contains)
        get("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToFind = Integer.parseInt(req.params("id")); //new
            Category foundCategory = categoryDao.findById(idOfCategoryToFind);
            model.put("category", foundCategory);
            List<Booking> allBookingByCategory = categoryDao.getAllBookingsByCategory(idOfCategoryToFind);
            model.put("bookings", allBookingByCategory);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "category-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category

        get("/categories/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editCategory", true);
            Category category = categoryDao.findById(Integer.parseInt(req.params("id")));
            model.put("category", category);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "category-form.hbs");
        }, new HandlebarsTemplateEngine());

//        //post: process a form to update a category

        post("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            categoryDao.update(idOfCategoryToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: delete an individual bookings

        get("/categories/:category_id/bookings/:booking_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfBookingToDelete = Integer.parseInt(req.params("booking_id"));
            bookingDao.deleteById(idOfBookingToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new booking form
        get("/bookings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> categories = categoryDao.getAll();
            model.put("categories", categories);
            return new ModelAndView(model, "booking-form.hbs");
        }, new HandlebarsTemplateEngine());

//        //task: process new booking form
        post("/bookings", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            String description = req.queryParams("description");
            int date = Integer.parseInt(req.queryParams("date"));
            int categoryId = Integer.parseInt(req.queryParams("categoryId"));
            Booking newBooking = new Booking(description, date, categoryId );        //See what we did with the hard coded categoryId?
            bookingDao.add(newBooking);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: show an individual booking that is nested in a category

        get("/categories/:category_id/bookings/:booking_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfBookingToFind = Integer.parseInt(req.params("booking_id")); //pull id - must match route segment
            Booking foundBooking = bookingDao.findById(idOfBookingToFind); //use it to find task
            int idOfCategoryToFind = Integer.parseInt(req.params("category_id"));
            Category foundCategory = categoryDao.findById(idOfCategoryToFind);
            model.put("category", foundCategory);
            model.put("booking", foundBooking); //add it to model for template to display
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "booking-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

//        //get: show a form to update a booking

        get("/bookings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            Booking booking = bookingDao.findById(Integer.parseInt(req.params("id")));
            model.put("booking", booking);
            model.put("editBooking", true);
            return new ModelAndView(model, "booking-form.hbs");
        }, new HandlebarsTemplateEngine());

        //: process a form to update a task
        post("/bookings/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int bookingToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newDate = Integer.parseInt(req.queryParams("date"));
            int newCategoryId = Integer.parseInt(req.queryParams("categoryId"));
            bookingDao.update(bookingToEditId, newContent, newDate, newCategoryId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());



    }
}
