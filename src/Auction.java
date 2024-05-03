import data.StaticRepository;
import models.Bid;
import models.Item;
import models.Participant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Auction {
    private final List<Item> items = StaticRepository.getItems();
    private final List<Participant> participants = StaticRepository.getParticipants();
    private final List<Item> soldItems = new ArrayList<>();
    private final List<Bid> bids = new ArrayList<>();

    private CountDownLatch pauseLatch = new CountDownLatch(1);

    // AUCTION CONFIG
    private static final double RISK_COEFFICIENT = 0.1; // percent from participant balance
    private static final int ITEM_SOLD_TIME = 5; // seconds

    private final ExecutorService executor = Executors.newCachedThreadPool();


    public void startAuction() {
        System.out.println("Auction started!");
        for (Item item : items) {
            executor.execute(() -> conductAuction(item));
            try {
                pauseLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println("Auction ended!");
        System.out.println("Auction info:");
        soldItems.forEach(System.out::println);
    }

    private void conductAuction(Item item) {
        System.out.println("Auction for " + item.name() + " started!");
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long startTime = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            if (System.currentTimeMillis() - startTime >= Auction.ITEM_SOLD_TIME * 1000) {
                sellItem(item);
                break;
            }
            try {
                var bidders = participants.stream().filter(b -> b.balance() > item.currentPrice()).toList();
                if (bidders.isEmpty()) {
                    if (System.currentTimeMillis() - startTime >= Auction.ITEM_SOLD_TIME * 1000) {
                        sellItem(item);
                        break;
                    }
                    continue;
                }
                var bidder = bidders.get(random.nextInt(bidders.size()));
                TimeUnit.SECONDS.sleep(random.nextInt(1, 2));
                double bidAmount = item.currentPrice() + random.nextDouble(1, (bidder.balance() - item.currentPrice()) * RISK_COEFFICIENT);
                Bid bid = new Bid(bidder, bidAmount, item);
                bids.add(bid);
                System.out.println(bidder.name() + " bid $" + bidAmount + " on " + item.name());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    private void sellItem(Item item) {
        synchronized (soldItems) {
            if (!soldItems.contains(item)) {
                soldItems.add(item);
                Participant winner = findWinner(item);
                if (winner != null) {
                    item.setOwner(winner);
                    System.out.println(item.name() + " sold to " + winner.name());
                    System.out.println(winner.name() + " balance: " + winner.balance());
                } else {
                    System.out.println("No bids for " + item.name() + ", item remains unsold.");
                }
                pauseLatch.countDown();
                pauseLatch = new CountDownLatch(1);
            }
        }
    }

    private Participant findWinner(Item item) {
        double highestBid = 0;
        Participant winner = null;
        for (Bid bid : bids) {
            if (bid.item().equals(item) && bid.amount() > highestBid) {
                highestBid = bid.amount();
                winner = bid.participant();
            }
        }
        return winner;
    }

    public static void main(String[] args) {
        Auction auction = new Auction();
        auction.startAuction();
    }
}
