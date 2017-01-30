package sample.PokerCalc;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alexey on 25.12.2016.
 */
public class Range {
    private ArrayList<Hand> handsArray = new ArrayList<>();
    private Random random;


    public Range(String s) {
        if (s.contains(",")) {

        String[] strings = s.split(",");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].contains("s")) {
                char fh = strings[i].charAt(0);
                char sh = strings[i].charAt(1);
                for (int j = 0; j < Card.suits.length; j++) {
                    Hand hand = new Hand(fh + Card.suits[j] + " " + sh + Card.suits[j]);
                    this.handsArray.add(hand);
                }
            } else if (strings[i].contains("o")) {
                char fh = strings[i].charAt(0);
                char sh = strings[i].charAt(1);
                for (int j = 0; j < Card.suits.length - 1; j++) {
                    for (int k = j + 1; k < Card.suits.length; k++) {
                        Hand hand = new Hand(fh + Card.suits[j] + " " + sh + Card.suits[k]);
                        Hand hand2 = new Hand(fh + Card.suits[k] + " " + sh + Card.suits[j]);
                        this.handsArray.add(hand);
                        this.handsArray.add(hand2);
                    }
                }
            } else {
                char fh = strings[i].charAt(0);
                char sh = strings[i].charAt(0);
                for (int j = 0; j < Card.suits.length; j++) {
                    for (int k = j + 1; k < Card.suits.length; k++) {
                        Hand hand = new Hand(fh + Card.suits[j] + " " + sh + Card.suits[k]);
                        this.handsArray.add(hand);
                    }
                }
            }
        }
    } else if (s.contains(" ")) {
            this.handsArray.add(new Hand(s));
        }
    }

    public void deleteCommonCards(ArrayList<Card> cardArrayList) {
        ArrayList<Hand> deleteInt = new ArrayList<>();
        for (Hand h : this.handsArray) {
            int[] indexes = h.getCardArray();
                for (Card cardFromArray : cardArrayList) {
                    if (indexes[1]==cardFromArray.getIndex() || indexes[2]==cardFromArray.getIndex()) {
                        deleteInt.add(h);
                    }
                }
        }
        for(Hand h : deleteInt) {
            this.handsArray.remove(h);
        }
    }

    public ArrayList<Hand> getHandsArray() {
        return handsArray;
    }

    public void setHandsArray(ArrayList<Hand> handsArray) {
        this.handsArray = handsArray;
    }

    public Hand getRandomHand() {
        return handsArray.get((int) random.nextDouble() * this.handsArray.size());
    }

    public int getRangeSize() {
        return handsArray.size();
    }
}
