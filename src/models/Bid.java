package models;

import exceptions.BidAmountMoreThenParticipantBalance;

public record Bid(
        Participant participant,
        double amount,
        Item item
) {
    public Bid(Participant participant, double amount, Item item) {
        this.participant = participant;
        this.amount = amount;
        this.item = item;

        if (participant.balance() < amount) {
            throw new BidAmountMoreThenParticipantBalance();
        }

        item.setCurrentPrice(amount);
    }
}
