package models;

import java.util.Objects;

public class Category {
    private int amount;
    private int id;

    public Category(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return amount == category.amount && id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, id);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
