package data;

import models.Item;
import models.Participant;

import java.util.ArrayList;
import java.util.List;

public class StaticRepository {
    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Smartphone", 50.0));
        items.add(new Item("Laptop", 100.0));
        items.add(new Item("Headphones", 20.0));
        items.add(new Item("Smartwatch", 30.0));
        items.add(new Item("Tablet", 40.0));
        return items;
    }

    public static List<Participant> getParticipants() {
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant("Alice", 1000.0));
        participants.add(new Participant("Bob", 1500.0));
        participants.add(new Participant("Charlie", 2000.0));
        participants.add(new Participant("Wolf", 1000.0));
        participants.add(new Participant("Genry", 1500.0));
        participants.add(new Participant("Jecky", 2000.0));
        participants.add(new Participant("Daniel", 1000.0));
        participants.add(new Participant("Alex", 1500.0));
        participants.add(new Participant("Frenck", 2000.0));
        return participants;
    }
}
