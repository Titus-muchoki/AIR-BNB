package dao;

import models.Booking;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oBookingDao implements BookingDao {

    private final Sql2o sql2o;

    public Sql2oBookingDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Booking booking) {
        String sql = "INSERT INTO bookings (description, categoryId) VALUES (:description, :categoryId)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(booking) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            booking.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Booking> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM bookings") //raw sql
                    .executeAndFetch(Booking.class); //fetch a list
        }
    }

    @Override
    public Booking findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM bookings WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Booking.class); //fetch an individual item
        }
    }
    @Override
    public void update(int id, String newDescription, int newCategoryId) {
        String sql = "UPDATE bookings SET (description, categoryId) = (:description, :categoryId) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("categoryId", newCategoryId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteById(int id) {
        String sql = "DELETE from bookings WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllBookings() {
        String sql = "DELETE from bookings";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
