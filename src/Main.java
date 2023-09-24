import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Players p1 = new Players("John Snow");
        Players p2 = new Players("Kamisato Ayaka");
        Deck deck = new Deck();
        List<String> matchingRanks1;
        List<String> matchingRanks2;
        String highestInput1="";
        String highestInput2="";
        String winnerName ="";
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

        if (length1 > 0) highestInput1 = (cardvalue1[0]);
        if (length2 > 0) highestInput2 = (cardvalue2[0]);

        System.out.println(matchingRanks1);
        System.out.println(matchingRanks2);

        int highestValue1 = p1.HighestValue(highestInput1);
        int highestValue2 = p2.HighestValue(highestInput2);



        if (p1.flush()) {
            System.out.println(p1.playername + " got a flush!");
            System.out.println(p1.playername + " has won!");
        }
        if (p2.flush()) {
            System.out.println(p2.playername+" got a flush!");
            System.out.println(p2.playername + " has won!");
        }

        switch (length1) {
            case 2:
                if (!p1.findMatchingRanks().isEmpty()) {
                    System.out.println(p1.playername + " has two pairs!: " + matchingRanks1);
                        if (length1 > length2) {
                            winnerName = p1.playername;
                            break;
                        }
                        else if (length1 == length2 && highestValue1 > highestValue2) {
                            winnerName = p1.playername;
                            break;
                        }
                        else
                            break;
                }
            case 1:
                if (!p1.findMatchingRanks().isEmpty()) {
                    System.out.println(p1.playername + " has a pair of: " + matchingRanks1);
                        if (length1 > length2) {
                            winnerName = p1.playername;
                            break;
                        }
                        else if (length1 == length2 && highestValue1 > highestValue2) {
                            winnerName = p1.playername;
                            break;
                        }
                        else
                            break;
                }
        }
        switch (length2) {
            case 2:
                if (!p2.findMatchingRanks().isEmpty()) {
                    System.out.println(p2.playername + " has two pairs!: " + matchingRanks2);
                        if (length2 > length1) {
                            winnerName = p2.playername;
                            break;
                        }
                            else if (length2 == length1 && highestValue2 > highestValue1) {
                            winnerName = p2.playername;
                            break;
                        }
                        else
                            break;
                }
            case 1:
                if (!p2.findMatchingRanks().isEmpty()) {
                    System.out.println(p2.playername + " has a pair of: " + matchingRanks2);
                        if (length2 > length1) {
                            winnerName = p2.playername;
                            break;
                        }
                        else if (length2 == length1 && highestValue2 > highestValue1) {
                            winnerName = p2.playername;
                            break;
                        }
                    else
                        break;
                }
        }
        if (winnerName.isEmpty() && !p1.flush() && !p2.flush()) {
            System.out.println("it's a tie!");
        }
        else {
            System.out.println(winnerName + " has won!");
        }
    }
}

class Players {
        String playername;
        static int numberofplayers = 0;
        int playernumber;
        List<String> playerhand;

    Players(String name) {
            numberofplayers++;
            playername = name;
            playernumber = numberofplayers;
            playerhand = new ArrayList<>();
        }

        void getCard(String deck) {
            playerhand.add(deck);

        }

        public void showHand() {
            System.out.println("Player Number: " + playernumber);
            System.out.println("Player Name: " + playername);
            System.out.println("Player Cards:");
            for (String deck : playerhand) {
                System.out.println(deck);
            }
            System.out.println();
        }

        String extractSuit(String deck) {
            String[] parts = deck.split(" ");
            return parts[2];
        }

        String extractRank(String deck) {
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
        int HighestValue(String highestInput) {
        int highestvalue=0;
            highestvalue = switch (highestInput) { //checks value of first pair
                case "2" -> 2;
                case "3" -> 3;
                case "4" -> 4;
                case "5" -> 5;
                case "6" -> 6;
                case "7" -> 7;
                case "8" -> 8;
                case "9" -> 9;
                case "10" -> 10;
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                case "A" -> 14;
                default -> highestvalue;
            };
        return highestvalue;
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




