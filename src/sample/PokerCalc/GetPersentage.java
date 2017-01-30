package sample.PokerCalc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alexey on 20.12.2016.
 */
public class GetPersentage {

    public ArrayList<Double> getPersentage(String range1, String range2, int trials, ArrayList<Card> postFlopCards) throws InterruptedException {
        ArrayList<Double> persentage = new ArrayList<>();
        Range firstRange = new Range(range1);
        Range secondRange = new Range(range2);
        if (postFlopCards.isEmpty()) {
            persentage = rangevsRangeGetPreflopPersentage(firstRange, secondRange, trials);
        } else if (!postFlopCards.isEmpty()) {
            persentage = rangevsRangeGetPostFlopPersentage(firstRange, secondRange, postFlopCards);
        }

        return persentage;
    }

    public ArrayList<Double> rangevsRangeGetPostFlopPersentage(Range range1, Range range2, ArrayList<Card> postFlopCards) throws InterruptedException {
        ArrayList<Double> persentageList = new ArrayList<>();

        double first = 0.0;
        double second = 0.0;
        double tie = 0.0;
        range1.deleteCommonCards(postFlopCards);
        range2.deleteCommonCards(postFlopCards);

        int size = 0;
        for (Hand h1 : range1.getHandsArray()) {
            for (Hand h2 : range2.getHandsArray()) {
                if (!h1.isCommonCard(h1, h2)) {
                    size++;
                    ArrayList<Double> persentage = new ArrayList<>();
                    if (postFlopCards.size() == 3) {
                        persentage = getFlopPersentage(new Hand(h1), new Hand(h2), postFlopCards);
                    } else if (postFlopCards.size() == 4) {
                        persentage = getTurnPersentage(new Hand(h1), new Hand(h2), postFlopCards);
                    } else if (postFlopCards.size() == 5) {
                        persentage = getRiverPersentage(new Hand(h1), new Hand(h2), postFlopCards);
                    }

                    if (!persentage.isEmpty()) {
                        first = first + persentage.get(0);
                        second = second + persentage.get(1);
                        tie = tie + persentage.get(2);
                    }
                }
            }
        }

        persentageList.add(first / size);
        persentageList.add(second / size);
        persentageList.add(tie / size);


        return persentageList;
    }


    public void addStatistics(Hand hand1, Hand hand2, int trials) throws IOException, InterruptedException {
        File file = new File("C:\\Users\\Alexey\\Desktop\\start.txt");

        FileOutputStream oFile = new FileOutputStream(file, false);
        for (int i = 0; i < trials; i++) {
            int x = randomWithRange(250, 20000);
            ArrayList<Double> persentage = getPreFlopPersentage(hand1, hand2, x);
            String s = x + ":" + persentage.get(0).toString();
            oFile.write(s.getBytes());
            oFile.write(System.getProperty("line.separator").getBytes());
        }
        oFile.flush();
        oFile.close();
    }


    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public ArrayList<Double> rangevsRangeGetPreflopPersentage(Range range1, Range range2, int trials) throws InterruptedException {
        ArrayList<Double> persentageList = new ArrayList<>();

        double first = 0.0;
        double second = 0.0;
        double tie = 0.0;

        int size = 0;
        for (Hand h1 : range1.getHandsArray()) {
            for (Hand h2 : range2.getHandsArray()) {
                if (!h1.isCommonCard(h1, h2)) {
                    size++;

                    ArrayList<Double> persentage = new ArrayList<>();
                    persentage = getPreFlopPersentage(new Hand(h1), new Hand(h2), trials);
                    if (!persentage.isEmpty()) {
                        first = first + persentage.get(0);
                        second = second + persentage.get(1);
                        tie = tie + persentage.get(2);
                    }
                } else {
                }
            }
        }


        persentageList.add(first / size);
        persentageList.add(second / size);
        persentageList.add(tie / size);


        return persentageList;
    }

    public ArrayList<Double> getPreFlopPersentage(Hand hand1, Hand hand2, int trials) throws InterruptedException {
        ArrayList<Double> persentageList = new ArrayList<>();
        int hands1 = 0;
        int hands2 = 0;
        int tie = 0;
        HandEvaluator handEv = new HandEvaluator();
        for (int i = 0; i < trials; i++) {
            Hand fhand = new Hand(hand1.toString());
            Hand shand = new Hand(hand2.toString());
            Deck deck = new Deck();
            deck.extractArrayHand(fhand);
            deck.extractArrayHand(shand);
            for (int j = 0; j < 5; j++) {
                Card card = deck.extractRandomArrayCard();
                fhand.addCard(card);
                shand.addCard(card);
            }
            int x = handEv.compareHands(fhand, shand);
            if (x == 2) {
                hands2++;
            } else if (x == 1) {
                hands1++;
            } else {
                tie++;
            }
        }
        double hand1win = (double) hands1 * 100 / trials;
        double hand2win = (double) hands2 * 100 / trials;
        double tie2 = (double) tie * 100 / trials;
        persentageList.add(hand1win);
        persentageList.add(hand2win);
        persentageList.add(tie2);
        return persentageList;
    }


    public static ArrayList<Double> getRiverPersentage(Hand hand1, Hand hand2, ArrayList<Card> postFlopCardList) throws InterruptedException {
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(0.0);
        arrayList.add(0.0);
        arrayList.add(0.0);

        for (int i = 0; i < postFlopCardList.size(); i++) {
            hand1.addCard(postFlopCardList.get(i));
            hand2.addCard(postFlopCardList.get(i));
        }
        HandEvaluator h = new HandEvaluator();
        int won = h.compareHands(hand1, hand2);
        if (won == 2) {
            arrayList.set(1, 100.0);
        } else if (won == 1) {
            arrayList.set(0, 100.0);
        } else if (won == 0) {
            arrayList.set(2, 100.0);
        }
        return arrayList;
    }

    public static ArrayList<Double> getTurnPersentage(Hand hand1, Hand hand2, ArrayList<Card> postFlopCardList) throws InterruptedException {
        ArrayList<Double> persentageList = new ArrayList<>();
        int hands1 = 0;
        int hands2 = 0;
        int tie = 0;
        HandEvaluator handEv = new HandEvaluator();
        Deck deck = new Deck();
        Hand firsthand = new Hand(hand1);
        Hand secondhand = new Hand(hand2);
        deck.extractArrayHand(firsthand);
        deck.extractArrayHand(secondhand);
        for (int j = 0; j < postFlopCardList.size(); j++) {
            deck.extractArrayCard(postFlopCardList.get(j));
            firsthand.addCard(postFlopCardList.get(j));
            secondhand.addCard(postFlopCardList.get(j));
        }
        ArrayList<Card> leftCard = deck.getCardsArray();
        for (int i = 0; i < leftCard.size(); i++) {
            Hand hand1toCompare = new Hand(firsthand);
            Hand hand2toCompare = new Hand(secondhand);
            hand1toCompare.addCard(leftCard.get(i));
            hand2toCompare.addCard(leftCard.get(i));
            int whowon = handEv.compareHands(hand1toCompare, hand2toCompare);

            if (whowon == 2) {
                hands2++;
            } else if (whowon == 1) {
                hands1++;
            } else {
                tie++;
            }
        }
        double hand1win = (double) hands1 * 100 / leftCard.size();
        double hand2win = (double) hands2 * 100 / leftCard.size();
        double tie2 = (double) tie * 100 / leftCard.size();
        persentageList.add(hand1win);
        persentageList.add(hand2win);
        persentageList.add(tie2);
        return persentageList;
    }

    public static ArrayList<Double> getFlopPersentage(Hand hand1, Hand hand2, ArrayList<Card> flopCardList) throws InterruptedException {
        ArrayList<Double> persentageList = new ArrayList<>();
        int trials = 45 * 22;
        int hands1 = 0;
        int hands2 = 0;
        int tie = 0;
        HandEvaluator handEv = new HandEvaluator();
        Deck deck = new Deck();
        deck.extractArrayHand(hand1);
        deck.extractArrayHand(hand2);
        for (int j = 0; j < flopCardList.size(); j++) {
            deck.extractArrayCard(flopCardList.get(j));
            hand1.addCard(flopCardList.get(j));
            hand2.addCard(flopCardList.get(j));
        }
        ArrayList<Card> leftCard = deck.getCardsArray();
        for (int i = 0; i < leftCard.size() - 1; i++) {
            for (int j = i + 1; j < leftCard.size(); j++) {
                Hand hand1toCompare = new Hand(hand1);
                Hand hand2toCompare = new Hand(hand2);

                hand1toCompare.addCard(leftCard.get(i));
                hand1toCompare.addCard(leftCard.get(j));

                hand2toCompare.addCard(leftCard.get(i));
                hand2toCompare.addCard(leftCard.get(j));

                int whowon = handEv.compareHands(hand1toCompare, hand2toCompare);

                if (whowon == 2) {
                    hands2++;
                } else if (whowon == 1) {
                    hands1++;
                } else {
                    tie++;
                }
            }
        }
        double hand1win = (double) hands1 * 100 / trials;
        double hand2win = (double) hands2 * 100 / trials;
        double tie2 = (double) tie * 100 / trials;
        persentageList.add(hand1win);
        persentageList.add(hand2win);
        persentageList.add(tie2);
        return persentageList;
    }
}
