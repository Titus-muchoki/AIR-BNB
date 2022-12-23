package models;
import java.util.Objects;

public class Booking {

    private String description;
    private boolean booked;
    private String startDate;
    private String endDate;
    private String clientName;
    private String email;

    private int id;
    private int categoryId;


    public Booking(String description, String startDate, String endDate, String clientName,  String email, int categoryId) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientName = clientName;
        this.email = email;
        this.booked = false;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return booked == booking.booked && id == booking.id && categoryId == booking.categoryId && Objects.equals(description, booking.description) && Objects.equals(startDate, booking.startDate) && Objects.equals(endDate, booking.endDate) && Objects.equals(clientName, booking.clientName) && Objects.equals(email, booking.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, booked, startDate, endDate, clientName, email, id, categoryId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
