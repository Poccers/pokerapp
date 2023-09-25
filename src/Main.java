import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        Players p1 = new Players("John Snow");
        Players p2 = new Players("Kamisato Ayaka");
        Deck deck = new Deck();
        List<String> matchingRanks1;
        List<String> matchingRanks2;
        String winnerName = "";
        deck.Shuffle();

        for (int i = 0; i < 5; i++) {
            p1.getCard(deck.DealCards());
            p2.getCard(deck.DealCards());
        }
        p1.showHand();
        p2.showHand();

        matchingRanks1 = p1.findMatchingRanks();
        matchingRanks2 = p2.findMatchingRanks();

        String[] cardvalue1 = matchingRanks1.toArray(new String[0]);
        String[] cardvalue2 = matchingRanks2.toArray(new String[0]);
        int length1 = cardvalue1.length;
        int length2 = cardvalue2.length;

        Players.Player player1 = new Players.Player(p1.playerhand);
        Players.Player player2 = new Players.Player(p2.playerhand);
        Players.Player c1 = new Players.Player(List.of(cardvalue1));
        Players.Player c2 = new Players.Player(List.of(cardvalue2));

        int comparisonResultValue = Players.compareHands(c1, c2);
        int comparisonResult = Players.compareHands(player1, player2);

        System.out.println();

        if (p1.flush()) {
            System.out.println(p1.playername + " got a flush!");
            winnerName = p1.playername;
        }
        switch (length1) {
            case 2:
                if (!p1.findMatchingRanks().isEmpty()) {
                    System.out.println(p1.playername + " has two pairs!: " + matchingRanks1);
                    if (length1 > length2 && !p1.flush() && !p2.flush()) {
                        winnerName = p1.playername;
                        break;
                    } else if (length1 == length2 && comparisonResultValue == 1 && !p1.flush() && !p2.flush()) {
                        winnerName = p1.playername;
                        break;
                    } else
                        break;
                }
            case 1:
                if (!p1.findMatchingRanks().isEmpty()) {
                    System.out.println(p1.playername + " has a pair of: " + matchingRanks1);
                    if (length1 > length2 && !p1.flush() && !p2.flush()) {
                        winnerName = p1.playername;
                        break;
                    } else if (length1 == length2 && comparisonResultValue == 1 && !p1.flush() && !p2.flush()) {
                        winnerName = p1.playername;
                        break;
                    } else if (length1 == length2 && comparisonResultValue == 0 && !p1.flush() && !p2.flush() && comparisonResult > 0) {
                        winnerName = p1.playername;
                    }
                        break;
                }
        }
        if (p2.flush()) {
            System.out.println(p2.playername + " got a flush!");
            winnerName = p2.playername;
        }
        switch (length2) {
            case 2:
                if (!p2.findMatchingRanks().isEmpty()) {
                    System.out.println(p2.playername + " has two pairs!: " + matchingRanks2);
                    if (length2 > length1  && !p1.flush() && !p2.flush()) {
                        winnerName = p2.playername;
                        break;
                    } else if (length2 == length1 && comparisonResultValue == -1 && !p1.flush() && !p2.flush()) {
                        winnerName = p2.playername;
                        break;
                    } else
                        break;
                }
            case 1:
                if (!p2.findMatchingRanks().isEmpty()) {
                    System.out.println(p2.playername + " has a pair of: " + matchingRanks2);
                    if (length2 > length1  && !p1.flush() && !p2.flush()) {
                        winnerName = p2.playername;
                        break;
                    } else if (length2 == length1 && comparisonResultValue == -1 && !p1.flush() && !p2.flush()) {
                        winnerName = p2.playername;
                        break;
                    } else if (length2 == length1 && comparisonResultValue == 0 && !p1.flush() && !p2.flush() && comparisonResult < 0) {
                        winnerName = p2.playername;
                    }
                        break;
                }
        }

        if (winnerName.isEmpty() && !p1.flush() && !p2.flush()) {
            if (comparisonResult > 0) {
                System.out.println(p1.playername+ " has won due to highest card!");
            }
            else if (comparisonResult < 0) {
                System.out.println(p2.playername+ " has won due to highest card!");
            }
            else if (comparisonResult == 0) {
                System.out.println("It's a Tie!");
            }
        }
        else {
            System.out.println();
            System.out.println(winnerName + " has won!");
        }
    }
}

class Players {
    String playername;
    List<String> playerhand;

    Players(String name) {
        playername = name;
        playerhand = new ArrayList<>();
    }

    public static class Player {
        private final List<String> hand;

        public Player(List<String> hand) {
            this.hand = hand;
        }

        public List<String> getHand() {
            return hand;
        }
    }

    public static int compareHands(Player player1, Player player2) {
        List<String> hand1 = player1.getHand();
        List<String> hand2 = player2.getHand();

        int highestValue1 = calculateHighestCardValue(hand1);
        int highestValue2 = calculateHighestCardValue(hand2);

        return Integer.compare(highestValue1, highestValue2);
    }
    void getCard(String deck) {
        playerhand.add(deck);

    }

    public void showHand() {
        System.out.println();
        System.out.println("Player Name: " + playername);
        System.out.println("Player Cards:");
        for (String deck : playerhand) {
            System.out.print(deck+", ");
        }
        System.out.println();
    }

    String extractSuit(String deck) {
        String[] parts = deck.split(" ");
        return parts[2];
    }

    static String extractRank(String deck) {
        String[] parts = deck.split(" ");
        return parts[0];
    }

    List<String> extractRanks(List<String> deck) {
        List<String> ranks = new ArrayList<>();

        for (String card : deck) {
            ranks.add(extractRank(card));
        }

        return ranks; // Add this return statement to return the ranks list.
    }

    boolean flush() {
        List<String> suits = new ArrayList<>();
        for (String deck : playerhand) {
            String suit = extractSuit(deck);
            suits.add(suit);
            String firstSuit = suits.get(0);

            if (!suit.equalsIgnoreCase(firstSuit)) {
                return false;
            }
        }
        return true;
    }

    List<String> findMatchingRanks() {
        List<String> matchingRanks = new ArrayList<>();
        List<String> ranks = extractRanks(playerhand);

        for (String rank : ranks) {
            int frequency = Collections.frequency(ranks, rank);
            if (frequency > 1 && !matchingRanks.contains(rank)) {
                matchingRanks.add(rank);
            }
        }

        return matchingRanks;
    }

    private static int calculateHighestCardValue(List<String> hand) {
        int highestValue = 0;

        for (String card : hand) {
            String rank = extractRank(card);
            int cardValue = cardValues.getOrDefault(rank, 0);
            highestValue = Math.max(highestValue, cardValue);
        }

        return highestValue;
    }

    private static final Map<String, Integer> cardValues = new HashMap<>();

    static {
        cardValues.put("2", 2);
        cardValues.put("3", 3);
        cardValues.put("4", 4);
        cardValues.put("5", 5);
        cardValues.put("6", 6);
        cardValues.put("7", 7);
        cardValues.put("8", 8);
        cardValues.put("9", 9);
        cardValues.put("10", 10);
        cardValues.put("J", 11);
        cardValues.put("Q", 12);
        cardValues.put("K", 13);
        cardValues.put("A", 14);
    }
}
class Deck {
    List<String> deck = new ArrayList<>();

    public Deck() {
        CreateDeck();

    }

    void CreateDeck() {
        String[] suit = " of Hearts, of Diamonds, of Clubs, of Spades".split(",");
        String[] rank = "A,2,3,4,5,6,7,8,9,10,J,Q,K".split(",");
        for (String s : suit)
            for (String v : rank)
                deck.add(v + s);
    }

    void Shuffle() {
        Collections.shuffle(deck);
    }

    public String DealCards() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        } else
            return null;
    }
}



