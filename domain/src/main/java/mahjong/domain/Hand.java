package mahjong.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mahjong.domain.Tile.Suit;
import mahjong.domain.Tile.Type;
import mahjong.domain.Tile.Value;

public class Hand {
    private ArrayList<Tile> hand = new ArrayList<Tile>();
    private boolean[] validatedHand;

    public Hand(ArrayList<Tile> hand) {
        this.hand = hand;
        this.createValidatedHandArray();

    }

    public ArrayList<Tile> getHand() {
        return this.hand;
    }

    public boolean[] getValidatedHandArray() {
        return this.validatedHand;
    }

    private void createValidatedHandArray() {
        this.validatedHand = new boolean[hand.size()];
        if (this.hand.size() > 0) {
            for (int i = 0; i < this.hand.size(); i++) {
                this.validatedHand[i] = false;
            }
        }
    }

    public boolean validateHandOfTiles() {
        boolean validation = false;
        if (this.validateAHandOfOrphans(0, 0)) {
            validation = true;
        } else if (this.validateHandForFourTripletsOrSequencesAndOnePair()) {
            validation = true;
        } else if (this.validateHandOfSevenPairs(0, 0)) {
            validation = true;
        }
        return validation;
    }

    private boolean validateHandForFourTripletsOrSequencesAndOnePair() {
        boolean validation = false;
        this.searchForAllSequencesInHandAndUpdateValidatedHandArray();
        this.checkForAllTripletsInHandAndUpdateValidatedHandArray();
        this.checkIfOneDoubleInHand();
        for (int i = 0; i < this.getValidatedHandArray().length; i++) {
            if (this.getValidatedHandArray()[i]) {
                validation = true;
            } else {
                validation = false;
            }
        }
        return validation;
    }

    private boolean validateHandOfSevenPairs(int index, int count) {
        if (count == 7) {
            return true;
        } else if (index == this.getHand().size()) {
            return false;
        } else if (this.getValidatedHandArray()[index]) {
            return validateHandOfSevenPairs(index + 1, count);
        } else if (this.checkIfSpecificTileIsPresentOnlyTwice(this.getHand().get(index), 0, 0)
                && !this.checkIfAPairIsPresentMoreThanOnce(this.getHand().get(index), 0, 0)) {
            count++;
            this.getValidatedHandArray()[index] = true;
            return validateHandOfSevenPairs(index + 1, count);
        } else {
            return validateHandOfSevenPairs(index + 1, count);
        }
    }

    private boolean validateAHandOfOrphans(int index, int count) {
        if (count == 12) {
            return checkIfOneDoubleInHand();
        } else if (index == this.getHand().size()) {
            return false;
        } else if (this.checkIfATileIsHonourTerminal(this.getHand().get(index))
                && this.checkTimesATileIsPresent(this.getHand().get(index), 0, 0) == 1) {
            count++;
            this.getValidatedHandArray()[index] = true;
            return validateAHandOfOrphans(index + 1, count);
        } else {
            return validateAHandOfOrphans(index + 1, count);
        }
    }

    private boolean checkIfATileIsHonourTerminal(Tile tile) {
        if (tile.getType() == Type.HONOUR || tile.getType() == Type.TERMINAL) {
            return true;
        } else {
            return false;
        }
    }

    private int checkTimesATileIsPresent(Tile tile, int index, int count) {
        if (index == this.getHand().size()) {
            return count;
        } else if (this.getHand().get(index).getSuit() == tile.getSuit()
                && this.getHand().get(index).getValue() == tile.getValue()) {
            count++;
            return checkTimesATileIsPresent(tile, index + 1, count);
        } else {
            return checkTimesATileIsPresent(tile, index + 1, count);
        }

    }

    public boolean checkIfSpecificTileIsPresentOnlyTwice(Tile tile, int handIndex, int count) {
        if (count == 2) {
            this.getValidatedHandArray()[handIndex - 1] = true;
            return true;
        } else if (handIndex == this.getHand().size()) {
            return false;
        } else if (this.getHand().get(handIndex).getSuit() == tile.getSuit()
                && this.getHand().get(handIndex).getValue() == tile.getValue()) {
            count++;
            return checkIfSpecificTileIsPresentOnlyTwice(tile, handIndex + 1, count);
        } else {
            return checkIfSpecificTileIsPresentOnlyTwice(tile, handIndex + 1, count);
        }
    }

    public boolean checkIfAPairIsPresentMoreThanOnce(Tile tile, int handIndex, int count) {
        if (count > 2) {
            return true;
        } else if (handIndex == this.getHand().size()) {
            return false;
        } else if (this.getHand().get(handIndex).getSuit() == tile.getSuit()
                && this.getHand().get(handIndex).getValue() == tile.getValue()) {
            count++;
            return checkIfAPairIsPresentMoreThanOnce(tile, handIndex + 1, count);
        } else {
            return checkIfAPairIsPresentMoreThanOnce(tile, handIndex + 1, count);
        }
    }

    public boolean checkIfDoubleOfValueAndUpdate(Value value, Suit suit) {
        int count = this.countNrOfCardsThathaveValue(value, suit);
        if (count == 2) {
            this.updateValidatedHandArrayOfTilesWithValue(value);
            return true;
        }
        return false;
    }

    public int countNrOfDoublesInSimpleTiles(Suit suit) {
        int doubleCount = 0;
        List<Value> listOfNumberValues = Arrays.asList(Value.ONE,
                Value.TWO, Value.THREE, Value.FOUR, Value.FIVE,
                Value.SIX, Value.SEVEN, Value.EIGHT, Value.NINE);
        for (int i = 0; i < listOfNumberValues.size(); i++) {
            if (this.checkIfDoubleOfValueAndUpdate(listOfNumberValues.get(i), suit)) {
                doubleCount++;
            }
        }
        return doubleCount;
    }

    public int countNrOfDoublesInHonourTiles(Suit suit) {
        int doubleCount = 0;
        List<Value> listOfHonour = Arrays.asList(Value.EAST,
                Value.WEST, Value.SOUTH, Value.NORTH, Value.RED,
                Value.GREEN, Value.WHITE);
        for (int i = 0; i < listOfHonour.size(); i++) {
            if (this.checkIfDoubleOfValueAndUpdate(listOfHonour.get(i), suit)) {
                doubleCount++;
            }
        }
        return doubleCount;
    }

    public boolean checkIfOneDoubleInHand() {
        int nrOfDoublesSimpleTiles = this.countNrOfDoublesInSimpleTiles(Suit.BAMBOO)
                + this.countNrOfDoublesInSimpleTiles(Suit.CHARACTER) + this.countNrOfDoublesInSimpleTiles(Suit.CIRCLE);
        int nrOfDoublesHonourTiles = this.countNrOfDoublesInHonourTiles(Suit.DRAGON)
                + this.countNrOfDoublesInHonourTiles(Suit.WIND);
        int totalNrOfDoubles = nrOfDoublesHonourTiles + nrOfDoublesSimpleTiles;
        if (totalNrOfDoubles == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int countNrOfCardsThathaveValue(Value value, Suit suit) {
        ArrayList<Tile> hand = this.getHand();
        int count = 0;
        for (int i = 0; i < hand.size(); i++) {
            Tile tile = hand.get(i);
            boolean validationTile = this.getValidatedHandArray()[i];
            if (tile.getValue() == value && tile.getSuit() == suit && !validationTile) {
                count++;
            }
        }
        return count;
    }

    public int checkForAllTripletsInHandAndUpdateValidatedHandArray() {
        int nrOfHonourTriplets = this.checkForAllTripletsInHonourTiles();
        int nrOfSimpleTriplets = this.checkForAllTripletsInSimpleTiles();
        int tripletCount = nrOfHonourTriplets + nrOfSimpleTriplets;
        return tripletCount;
    }

    public int checkForAllTripletInSpecificSimpleSuit(Suit suit) {
        int tripletCount = 0;
        List<Value> listOfNumberValues = Arrays.asList(Value.ONE,
                Value.TWO, Value.THREE, Value.FOUR, Value.FIVE,
                Value.SIX, Value.SEVEN, Value.EIGHT, Value.NINE);
        for (int i = 0; i < listOfNumberValues.size(); i++) {
            if (this.checkIfTripletOfValue(listOfNumberValues.get(i), suit)) {
                tripletCount++;
            }
        }
        return tripletCount;
    }

    public int checkForAllTripletsInSimpleTiles() {
        int tripletCount = this.checkForAllTripletInSpecificSimpleSuit(Suit.BAMBOO)
                + this.checkForAllTripletInSpecificSimpleSuit(Suit.CHARACTER)
                + this.checkForAllTripletInSpecificSimpleSuit(Suit.CIRCLE);
        return tripletCount;
    }

    public int checkForAllTripletInWindTiles() {
        int tripletCount = 0;
        List<Value> listOfHonour = Arrays.asList(Value.EAST,
                Value.WEST, Value.SOUTH, Value.NORTH);
        for (int i = 0; i < listOfHonour.size(); i++) {
            if (this.checkIfTripletOfValue(listOfHonour.get(i), Suit.WIND)) {
                tripletCount++;
            }
        }
        return tripletCount;
    }

    public int checkForAllTripletInDragonTiles() {
        int tripletCount = 0;
        List<Value> listOfHonour = Arrays.asList(Value.RED,
                Value.GREEN, Value.WHITE);
        for (int i = 0; i < listOfHonour.size(); i++) {
            if (this.checkIfTripletOfValue(listOfHonour.get(i), Suit.DRAGON)) {
                tripletCount++;
            }
        }
        return tripletCount;
    }

    public int checkForAllTripletsInHonourTiles() {
        int tripletCount = this.checkForAllTripletInDragonTiles() + this.checkForAllTripletInWindTiles();
        return tripletCount;
    }

    public boolean checkIfTripletOfValue(Value value, Suit suit) {
        int count = this.countNrOfCardsThathaveValue(value, suit);
        if (count == 3) {
            this.updateValidatedHandArrayOfTilesWithValue(value);
            return true;
        }
        return false;
    }

    public void updateValidatedHandArrayOfTilesWithValue(Value value) {
        ArrayList<Tile> hand = this.getHand();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == value) {
                this.getValidatedHandArray()[i] = true;
            }
        }
    }

    public boolean checkIfTileToCompareHasValueNextToFirstTile(int indexFirstTile, int indexTileToCompare) {
        ArrayList<Tile> hand = this.getHand();
        Tile firstTile = hand.get(indexFirstTile);
        Tile tileToCompare = hand.get(indexTileToCompare);
        if (firstTile.getValue() == Value.NINE) {
            return false;
        }
        if (tileToCompare.getValue() == firstTile.getValue().next().get()) {
            return true;
        }
        return false;
    }

    public boolean checkIfTileToCompareHasValueBeforeFirstTile(int indexFirstTile, int indexTileToCompare) {
        ArrayList<Tile> hand = this.getHand();
        Tile firstTile = hand.get(indexFirstTile);
        Tile tileToCompare = hand.get(indexTileToCompare);
        if (firstTile.getValue() == Value.ONE) {
            return false;
        }
        if (tileToCompare.getValue() == firstTile.getValue().previous().get()) {
            return true;
        }
        return false;
    }

    public boolean checkIfTileIsHonour(int index) {
        if (this.getHand().get(index).getType() == Type.HONOUR) {
            return true;
        }
        return false;
    }

    public boolean checkIfSequenceOfThreeIsPresentInHand(int indexFirstTile, int indexTileToCompare) {
        if (indexFirstTile == this.getHand().size()) {
            return false;
        } else if (this.getValidatedHandArray()[indexFirstTile]) {
            return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile + 1, indexTileToCompare);
        } else if (!this.checkIfTileIsHonour(indexFirstTile)) {
            return this.handleCheckingIfSecondAndThirdTileAreSeqeunceOfFirstTile(indexFirstTile, indexTileToCompare);
        } else {
            return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile + 1, indexTileToCompare);
        }
    }

    public boolean handleCheckingIfSecondAndThirdTileAreSeqeunceOfFirstTile(int indexFirstTile,
            int indexTileToCompare) {
        if (indexTileToCompare == this.getHand().size()) {
            indexTileToCompare = 0;
            return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile + 1, indexTileToCompare);
        } else if (this.getValidatedHandArray()[indexTileToCompare]) {
            return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile, indexTileToCompare + 1);
        } else if (!this.checkIfTileIsHonour(indexTileToCompare)) {
            if (this.checkIfTileToCompareHasValueNextToFirstTile(indexFirstTile, indexTileToCompare)) {
                if (checkIfThirdTileOfSequenceIsPresent(indexFirstTile, indexTileToCompare, 0)) {
                    return true;
                } else {
                    return false;
                }
            } else if (this.checkIfTileToCompareHasValueBeforeFirstTile(indexFirstTile, indexTileToCompare)) {
                if (checkIfThirdTileOfSequenceIsPresent(indexTileToCompare, indexFirstTile, 0)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile, indexTileToCompare + 1);
            }
        } else {
            return checkIfSequenceOfThreeIsPresentInHand(indexFirstTile, indexTileToCompare + 1);
        }
    }

    public boolean checkIfThirdTileOfSequenceIsPresent(int indexFirstTile, int indexSecondTile, int indexThirdTile) {
        if (indexThirdTile == this.getHand().size()) {
            return false;
        } else if (this.getValidatedHandArray()[indexThirdTile]) {
            return checkIfThirdTileOfSequenceIsPresent(indexFirstTile, indexSecondTile, indexThirdTile + 1);
        } else if (checkIfTileToCompareHasValueNextToFirstTile(indexSecondTile, indexThirdTile)) {
            this.getValidatedHandArray()[indexFirstTile] = true;
            this.getValidatedHandArray()[indexSecondTile] = true;
            this.getValidatedHandArray()[indexThirdTile] = true;
            return true;
        } else {
            return checkIfThirdTileOfSequenceIsPresent(indexFirstTile, indexSecondTile, indexThirdTile + 1);
        }
    }

    public int searchForAllSequencesInHandAndUpdateValidatedHandArray() {
        int i = 0;
        int sequenceCount = 0;
        while (i < 4) {
            if (this.checkIfSequenceOfThreeIsPresentInHand(0, 0)) {
                sequenceCount++;
            }
            i++;
        }
        return sequenceCount;
    }

}
