package models;

import java.util.Objects;

public final class Item {
    private final String name;
    private final double startPrice;
    private double currentPrice;
    private Participant owner = null;

    public Item(
            String name,
            double startPrice
    ) {
        this.name = name;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
    }

    public String name() {
        return name;
    }

    public double startPrice() {
        return startPrice;
    }

    public double currentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setOwner(Participant owner) {
        this.owner = owner;
        owner.setBalance(owner.balance() - currentPrice);
    }

    public Participant owner() {
        return owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Item) obj;
        return Objects.equals(this.name, that.name) &&
                Double.doubleToLongBits(this.startPrice) == Double.doubleToLongBits(that.startPrice) &&
                Double.doubleToLongBits(this.currentPrice) == Double.doubleToLongBits(that.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startPrice, currentPrice);
    }

    @Override
    public String toString() {
        String ownerName = (owner != null) ? owner.name() : "None";
        return "Item: " + name + ", Start Price: $" + startPrice + ", Current Price: $" + currentPrice + ", Owner: " + ownerName + ".";
    }


}
