package mahjong.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import mahjong.domain.Tile.Suit;
import mahjong.domain.Tile.Value;

public class Mahjong {
    private ArrayList<String> listOfAllMahjongHands = new ArrayList<String>();
    private ArrayList<int[]> allHandsConverted = new ArrayList<int[]>();
    private ArrayList<Hand> allHands = new ArrayList<Hand>();

    public Mahjong(File mahjongHandFile) {
        this.listOfAllMahjongHands = this.readFile(mahjongHandFile);
        this.allHandsConverted = this.convertAllMahjongHandsIntoHEX(listOfAllMahjongHands);
        this.createAllHandsAndAddToArrayList();
    }

    public static void main(String[] args) {
        File exampleHands = new File("domain/resources/mahjong_example_hands");
        File exampleNineGates = new File("domain/resources/mahjong_nine_gates");
        File exampleInvalidHands = new File("domain/resources/mahjong_unlegit_hands");
        Mahjong mahjongValidExampleOne = new Mahjong(exampleHands);
        Mahjong mahjongValidExampleTwo = new Mahjong(exampleNineGates);
        Mahjong mahjongInvalidExample = new Mahjong(exampleInvalidHands);
        System.out.println("Checking the validity of the hands of the mahjong_example_hands file...");
        mahjongValidExampleOne.printResultValidation();
        System.out.println("Checking the validity of the hands of the mahjong_nine_gates file...");
        mahjongValidExampleTwo.printResultValidation();
        System.out.println("Checking the validity of the hands of the mahjong_unlegit_hands file...");
        mahjongInvalidExample.printResultValidation();
    }

    private void printResultValidation() {
        boolean validation = this.validateAnArrayListOfHands();
        if (validation) {
            System.out.println("...The hands are all valid!");
        } else {
            System.out.println("At least one of the hands is invalid...");
        }
    }

    public boolean validateAnArrayListOfHands() {
        int validationCount = 0;
        for (int i = 0; i < this.getListOfAllHands().size(); i++) {
            if (this.getListOfAllHands().get(i).validateHandOfTiles()) {
                validationCount++;
            }
        }
        if (validationCount == this.getListOfAllHands().size()) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<String> getListOfAllMahjongHands() {
        return this.listOfAllMahjongHands;
    }

    public ArrayList<int[]> getListOfAllConvertedHands() {
        return this.allHandsConverted;
    }

    public ArrayList<Hand> getListOfAllHands() {
        return this.allHands;
    }

    private void createAllHandsAndAddToArrayList() {
        for (int i = 0; i < this.getListOfAllConvertedHands().size(); i++) {
            Hand hand = this.createHandFromConvertedTiles(i);
            this.getListOfAllHands().add(hand);
        }
    }

    public ArrayList<String> readFile(File mahjongHandFile) {
        ArrayList<String> mahjongList = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(mahjongHandFile);
            while (scanner.hasNextLine()) {
                String hand = scanner.nextLine();
                mahjongList.add(hand);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return mahjongList;
    }

    public Hand createHandFromConvertedTiles(int indexHand) {
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        ArrayList<Tile> convertedHandOfTiles = this.convertIntToTilesForOneHand(indexHand, 0, handOfTiles);
        Hand hand = new Hand(convertedHandOfTiles);
        return hand;
    }

    private ArrayList<Tile> convertIntToTilesForOneHand(int indexHand, int indexTile, ArrayList<Tile> handOfTiles) {
        if (indexTile > this.getListOfAllConvertedHands().get(indexHand).length - 1) {
            return handOfTiles;
        }
        int tileValue = this.getListOfAllConvertedHands().get(indexHand)[indexTile];
        if (checkIfTileValueIsBetweenBoundaries(0, 3, indexHand, indexTile)) {
            Tile tile = convertTileValueToCorrectTile(Suit.WIND, tileValue);
            handOfTiles.add(tile);
            return this.convertIntToTilesForOneHand(indexHand, indexTile + 1, handOfTiles);
        } else if (checkIfTileValueIsBetweenBoundaries(4, 6, indexHand, indexTile)) {
            Tile tile = convertTileValueToCorrectTile(Suit.DRAGON, tileValue);
            handOfTiles.add(tile);
            return this.convertIntToTilesForOneHand(indexHand, indexTile + 1, handOfTiles);
        } else if (checkIfTileValueIsBetweenBoundaries(7, 15, indexHand, indexTile)) {
            Tile tile = convertTileValueToCorrectTile(Suit.CHARACTER, tileValue);
            handOfTiles.add(tile);
            return this.convertIntToTilesForOneHand(indexHand, indexTile + 1, handOfTiles);
        } else if (checkIfTileValueIsBetweenBoundaries(16, 24, indexHand, indexTile)) {
            Tile tile = convertTileValueToCorrectTile(Suit.BAMBOO, tileValue);
            handOfTiles.add(tile);
            return this.convertIntToTilesForOneHand(indexHand, indexTile + 1, handOfTiles);
        } else if (checkIfTileValueIsBetweenBoundaries(25, 33, indexHand, indexTile)) {
            Tile tile = convertTileValueToCorrectTile(Suit.CIRCLE, tileValue);
            handOfTiles.add(tile);
            return this.convertIntToTilesForOneHand(indexHand, indexTile + 1, handOfTiles);
        } else {
            return handOfTiles;
        }
    }

    private Tile convertTileValueToCorrectTile(Suit suit, int tileValue) {
        switch (suit) {
            case WIND:
                Tile windTile = this.convertToWindTile(tileValue);
                return windTile;
            case DRAGON:
                Tile dragonTile = this.convertToDragonTile(tileValue);
                return dragonTile;
            default:
                Tile simpleTile = this.convertToSimpleTile(tileValue, suit);
                return simpleTile;
        }
    }

    private boolean checkIfTileValueIsBetweenBoundaries(int lowerBound, int upperBound, int indexHand, int indexTile) {
        if (this.getListOfAllConvertedHands().get(indexHand)[indexTile] >= lowerBound
                && this.getListOfAllConvertedHands().get(indexHand)[indexTile] <= upperBound) {
            return true;
        } else {
            return false;
        }
    }

    private Tile convertToWindTile(int tileValue) {
        switch (tileValue) {
            case 0:
                Tile eastTile = new Tile(Value.EAST, Suit.WIND);
                return eastTile;
            case 1:
                Tile southTile = new Tile(Value.SOUTH, Suit.WIND);
                return southTile;
            case 2:
                Tile westTile = new Tile(Value.WEST, Suit.WIND);
                return westTile;
            case 3:
                Tile northTile = new Tile(Value.NORTH, Suit.WIND);
                return northTile;
            default:
                throw new IllegalArgumentException("Something went wrong! Invalid Tile");
        }
    }

    private Tile convertToDragonTile(int tileValue) {
        switch (tileValue) {
            case 4:
                Tile redTile = new Tile(Value.RED, Suit.DRAGON);
                return redTile;
            case 5:
                Tile greenTile = new Tile(Value.GREEN, Suit.DRAGON);
                return greenTile;
            case 6:
                Tile whiteTile = new Tile(Value.WHITE, Suit.DRAGON);
                return whiteTile;
            default:
                throw new IllegalArgumentException("Something went wrong! Invalid Tile");
        }
    }

    private Tile convertToSimpleTile(int tileValue, Suit suit) {
        if (suit == Suit.BAMBOO) {
            tileValue = tileValue - 9;
        }
        if (suit == Suit.CIRCLE) {
            tileValue = tileValue - 18;
        }
        switch (tileValue) {
            case 7:
                Tile oneTile = new Tile(Value.ONE, suit);
                return oneTile;
            case 8:
                Tile twoTile = new Tile(Value.TWO, suit);
                return twoTile;
            case 9:
                Tile threeTile = new Tile(Value.THREE, suit);
                return threeTile;
            case 10:
                Tile fourTile = new Tile(Value.FOUR, suit);
                return fourTile;
            case 11:
                Tile fiveTile = new Tile(Value.FIVE, suit);
                return fiveTile;
            case 12:
                Tile sixTile = new Tile(Value.SIX, suit);
                return sixTile;
            case 13:
                Tile sevenTile = new Tile(Value.SEVEN, suit);
                return sevenTile;
            case 14:
                Tile eightTile = new Tile(Value.EIGHT, suit);
                return eightTile;
            case 15:
                Tile nineTile = new Tile(Value.NINE, suit);
                return nineTile;
            default:
                throw new IllegalArgumentException("Something went wrong! Invalid Tile");
        }
    }

    private String[] divideIntoStringArrays(int index, ArrayList<String> listOfHands) {
        String handInStringFormat = listOfHands.get(index);
        String[] hand = handInStringFormat.split(" ");
        return hand;
    }

    private int[] convertFromEmojiToInt(String[] unconvertedHand) {
        int[] hand = new int[14];
        for (int i = 0; i < unconvertedHand.length; i++) {
            String unconvertedTile = unconvertedHand[i];
            int codePoint = unconvertedTile.codePointAt(0);
            String codePointString = Integer.toHexString(codePoint).toUpperCase();
            String codePointStringHEX = codePointString.substring(3);
            int codePointToInt = this.convertHEXToInt(codePointStringHEX);
            hand[i] = codePointToInt;
        }
        return hand;
    }

    private ArrayList<int[]> convertAllMahjongHandsIntoHEX(ArrayList<String> listOfHands) {
        ArrayList<int[]> allHands = new ArrayList<int[]>();
        for (int i = 0; i < listOfHands.size(); i++) {
            String[] oneHandUnconverted = this.divideIntoStringArrays(i, listOfHands);
            int[] oneHandConverted = this.convertFromEmojiToInt(oneHandUnconverted);
            allHands.add(oneHandConverted);
        }
        return allHands;
    }

    private int convertHEXToInt(String hexString) {
        int value = Integer.parseInt(hexString, 16);
        return value;
    }

}
