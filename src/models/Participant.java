package models;

import java.util.Objects;

public final class Participant {
    private final String name;
    private double balance;

    public Participant(
            String name,
            double balance
    ) {
        this.name = name;
        this.balance = balance;
    }

    public String name() {
        return name;
    }

    public double balance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Participant) obj;
        return Objects.equals(this.name, that.name) &&
                Double.doubleToLongBits(this.balance) == Double.doubleToLongBits(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, balance);
    }

    @Override
    public String toString() {
        return "Participant[" +
                "name=" + name + ", " +
                "balance=" + balance + ']';
    }

}
