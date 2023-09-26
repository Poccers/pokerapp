import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        Players p1 = new Players("John Snow");
        Players p2 = new Players("Kamisato Ayaka");
        Deck deck = new Deck();
        List<String> matchingRanks1;
        List<String> matchingRanks2;
        deck.Shuffle();

        for (int i = 0; i < 5; i++) {
            p1.getCard(deck.DealCards());
            p2.getCard(deck.DealCards());
        }

        matchingRanks1 = p1.findMatchingRanks();
        matchingRanks2 = p2.findMatchingRanks();

        String[] cardvalue1 = matchingRanks1.toArray(new String[0]);
        String[] cardvalue2 = matchingRanks2.toArray(new String[0]);
        int length1 = cardvalue1.length;
        int length2 = cardvalue2.length;
        Players.Player c1 = new Players.Player(List.of(cardvalue1));
        Players.Player c2 = new Players.Player(List.of(cardvalue2));

        Players.Player player1 = new Players.Player(p1.extractRanks(p1.playerhand));
        Players.Player player2 = new Players.Player(p2.extractRanks(p2.playerhand));

        int duplicateCards1 = player1.findDuplicateCardCount();
        int duplicateCards2 = player2.findDuplicateCardCount();

        boolean isStraight1 = player1.isStraight(player1.getHand());
        boolean isStraight2 = player2.isStraight(player2.getHand());

        boolean straightFlush1 = p1.StraightFlush(isStraight1, p1.flush());
        boolean straightFlush2 = p2.StraightFlush(isStraight2, p1.flush());

        int comparisonResultValue = Players.compareHands(c1, c2);
        int comparisonResult = Players.compareHands(player1, player2);

        FindWinner w1 = new FindWinner(p1.playername, p2.playername, straightFlush1, straightFlush2, isStraight1, isStraight2, duplicateCards1, duplicateCards2, p1.flush(), p2.flush(), length1, length2, p1.findMatchingRanks(), p2.findMatchingRanks(), matchingRanks1, matchingRanks2, comparisonResultValue, comparisonResult);

        p1.showHand();
        p2.showHand();
        System.out.println();
        w1.findHand();
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
        public int findDuplicateCardCount() {
            Map<String, Integer> cardCounts = new HashMap<>();

            for (String card : hand) {
                cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
            }
            int highestCount = 0;
            for (int count : cardCounts.values()) {
                if (count > highestCount) {
                    highestCount = count;
                }
            }

            return highestCount;
        }
        public boolean isStraight(List<String> hand) {
            if (hand == null || hand.size() != 5) {
                return false;
            } else {

                int[] values = new int[5];
                int index = 0;

                for (String card : hand) {
                    String rank = extractRank(card);
                    values[index++] = cardValues.getOrDefault(rank, 0);
                }
                Arrays.sort(values);
                int prev = -1;

                for (int i = 0; i < values.length; i++) {

                    if (prev == -1 || (prev + 1) == values[i]) {
                        prev = values[i];
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    public boolean StraightFlush(boolean straight, boolean flush) {
        boolean straightflush = false;
        if (straight && flush) {
            straightflush = true;
        }
        return straightflush;
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

        return ranks;
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

class FindWinner {
    String winnerName;
    List<String> findMatchingRanks1;
    List<String> findMatchingRanks2;
    List<String> matchingRanks1;
    List<String> matchingRanks2;
    String playername1;
    String playername2;
    int duplicateCards1;
    int duplicateCards2;
    boolean isStraight1;
    boolean isStraight2;
    boolean flush1;
    boolean flush2;
    int length1;
    int length2;
    int comparisonResult;
    int comparisonResultValue;
    boolean straightFlush1;
    boolean straightFlush2;

    FindWinner(String playername1, String playername2, boolean straightFlush1, boolean straightFlush2, boolean straight1, boolean straight2, int duplicateCards1, int duplicateCards2, boolean flush1, boolean flush2, int length1, int length2, List<String> findMatchingRanks1, List<String> findMatchingRanks2, List<String> matchingRanks1, List<String> matchingRanks2, int comparisonResultValue, int comparisonResult) {
        this.playername1 = playername1;
        this.playername2 = playername2;
        this.duplicateCards1 = duplicateCards1;
        this.duplicateCards2 = duplicateCards2;
        this.isStraight1 = straight1;
        this.isStraight2 = straight2;
        this.flush1 = flush1;
        this.flush2 = flush2;
        this.length1 = length1;
        this.length2 = length2;
        this.comparisonResult = comparisonResult;
        this.comparisonResultValue = comparisonResult;
        this.straightFlush1 = straightFlush1;
        this.straightFlush2 = straightFlush2;
        this.findMatchingRanks1 = findMatchingRanks1;
        this.findMatchingRanks2 = findMatchingRanks2;
        this.matchingRanks1 = matchingRanks1;
        this.matchingRanks2 = matchingRanks2;


    }

    void findHand() {

        if (straightFlush1) {
            System.out.println(playername1 + " got a straight flush!");
            winnerName = playername1;
        } else if (duplicateCards1 == 4 && !straightFlush2) {
            System.out.println(playername1 + " has 4 of a kind!");
            winnerName = playername1;
        } else if (flush1 && !straightFlush2 && duplicateCards2 < 4) {
            System.out.println(playername1 + " got a flush!");
            winnerName = playername1;
        } else if (isStraight1 && !flush2 && duplicateCards2 < 4 && !straightFlush2) {
            System.out.println(playername1 + " got a straight!");
            winnerName = playername1;
        } else if (duplicateCards1 == 3 && !isStraight2 && !flush2 && !straightFlush2) {
            System.out.println(playername1 + " has three of a kind!");
            winnerName = playername1;
        } else {
            switch (length1) {
                case 2:
                    if (!findMatchingRanks1.isEmpty()) {
                        System.out.println(playername1 + " has two pairs!: " + matchingRanks1);
                        if (length1 > length2 && !isStraight2 && !flush2 && duplicateCards2 < 3 && !straightFlush2) {
                            winnerName = playername1;
                            break;
                        } else if (length1 == length2 && comparisonResultValue == 1 && !isStraight2 && !flush2 && duplicateCards2 < 3 && !straightFlush2) {
                            winnerName = playername1;
                            break;
                        } else
                            break;
                    }
                case 1:
                    if (!findMatchingRanks1.isEmpty()) {
                        System.out.println(playername1 + " has a pair of: " + matchingRanks1);
                        if (length1 > length2 && !isStraight2 && !flush2 && duplicateCards2 < 3 && !straightFlush2) {
                            winnerName = playername1;
                            break;
                        } else if (length1 == length2 && comparisonResultValue == 1 && !isStraight2 && !flush2 && duplicateCards2 < 3 && !straightFlush2) {
                            winnerName = playername1;
                            break;
                        } else if (length1 == length2 && comparisonResultValue == 0 && comparisonResult > 0 && !isStraight2 && !flush2 && duplicateCards2 < 3 && !straightFlush2) {
                            winnerName = playername1;
                        }
                        break;
                    }
            }
        }
        if (straightFlush2) {
            System.out.println(playername2 + " got a straight flush!");
            winnerName = playername2;
        } else if (duplicateCards2 == 4 && !straightFlush1) {
            System.out.println(playername2 + " has 4 of a kind!");
            winnerName = playername2;
        } else if (flush2 && !flush1 && duplicateCards1 < 4 && !straightFlush1) {
            System.out.println(playername2 + " got a flush!");
            winnerName = playername2;
        } else if (isStraight2 && !flush1 && duplicateCards1 < 4 && !straightFlush1) {
            System.out.println(playername2 + " got a straight!");
            winnerName = playername2;
        } else if (duplicateCards2 == 3 && !isStraight1 && !flush1 && !straightFlush1) {
            System.out.println(playername2 + " has three of a kind!");
            winnerName = playername2;
        } else {
            switch (length2) {
                case 2:
                    if (!findMatchingRanks2.isEmpty()) {
                        System.out.println(playername2 + " has two pairs!: " + matchingRanks2);
                        if (length2 > length1 && !isStraight1 && !flush1 && duplicateCards1 < 3 && !straightFlush1) {
                            winnerName = playername2;
                            break;
                        } else if (length2 == length1 && comparisonResultValue == -1 && !isStraight1 && !flush1 && duplicateCards1 < 3 && !straightFlush1) {
                            winnerName = playername2;
                            break;
                        } else
                            break;
                    }
                case 1:
                    if (!findMatchingRanks2.isEmpty()) {
                        System.out.println(playername2 + " has a pair of: " + matchingRanks2);
                        if (length2 > length1 && !isStraight1 && !flush1 && duplicateCards1 < 3 && !straightFlush1) {
                            winnerName = playername2;
                            break;
                        } else if (length2 == length1 && comparisonResultValue == -1 && !isStraight1 && !flush1 && duplicateCards1 < 3 && !straightFlush1) {
                            winnerName = playername2;
                            break;
                        } else if (length2 == length1 && comparisonResultValue == 0 && comparisonResult < 0 && !isStraight1 && !flush1 && duplicateCards1 < 3 && !straightFlush1) {
                            winnerName = playername2;
                        }
                        break;
                    }
            }
        }
        if (winnerName.isEmpty()) {
            if (comparisonResult > 0) {
                System.out.println(playername1 + " has won due to highest card!");
            } else if (comparisonResult < 0) {
                System.out.println(playername2 + " has won due to highest card!");
            } else if (comparisonResult == 0) {
                System.out.println("It's a Tie!");
            }
        } else {
            System.out.println();
            System.out.println(winnerName + " has won!");
        }
    }
}