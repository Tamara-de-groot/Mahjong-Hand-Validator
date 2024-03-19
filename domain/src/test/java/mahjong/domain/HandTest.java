package mahjong.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import mahjong.domain.Tile.Suit;
import mahjong.domain.Tile.Value;

public class HandTest {

    @Test
    public void testIfTwoWhiteTilesReturnsCountOfTwo() {
        Tile tile = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile2 = new Tile(Value.WHITE, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        Hand hand = new Hand(handOfTiles);
        assertEquals(2, hand.countNrOfCardsThathaveValue(Value.WHITE, Suit.DRAGON));
        assertEquals(0, hand.countNrOfCardsThathaveValue(Value.EAST, Suit.WIND));
    }

    @Test
    public void threeOfSameValueReturnsTrue() {
        Tile tile = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile2 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile3 = new Tile(Value.WHITE, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfTripletOfValue(Value.WHITE, Suit.DRAGON));
        assertFalse(hand.checkIfTripletOfValue(Value.EAST, Suit.WIND));
    }

    @Test
    public void testIfThreeTripletsInNumbersReturnsThree() {
        Tile tile = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.TWO, Suit.CIRCLE);
        Tile tile3 = new Tile(Value.THREE, Suit.CHARACTER);
        Tile tile4 = new Tile(Value.FOUR, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile4);
        Hand hand = new Hand(handOfTiles);
        assertEquals(3, hand.checkForAllTripletsInSimpleTiles());
    }

    @Test
    public void testIfThreeTripletsInHonoursReturnsThree() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.WEST, Suit.WIND);
        Tile tile3 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile4 = new Tile(Value.FOUR, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile4);
        Hand hand = new Hand(handOfTiles);
        assertEquals(3, hand.checkForAllTripletsInHonourTiles());
    }

    @Test
    public void testIfTwoDoublesReturnsFalse() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.THREE, Suit.CIRCLE);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        Hand hand = new Hand(handOfTiles);
        assertFalse(hand.checkIfOneDoubleInHand());
    }

    @Test
    public void testIfTileWithValueOneIsFollowedByTileWithValueTwoReturnsTrue() {
        Tile tile = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.TWO, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfTileToCompareHasValueNextToFirstTile(0, 1));
    }

    @Test
    public void testIfReturnsTrueWhenASequenceOfThreeIsPresentInHand() {
        Tile tile = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.TWO, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile4 = new Tile(Value.GREEN, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        handOfTiles.add(tile4);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfSequenceOfThreeIsPresentInHand(0, 0));
    }

    @Test
    public void testIfReturnsTrueWhenASequenceOfThreeIsPresentInHandWhenHandOf14() {
        ArrayList<Tile> handOfTiles = createMockHandForSequencetesting();
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfSequenceOfThreeIsPresentInHand(0, 0));
    }

    @Test
    public void testNoSequenceOfThreeReturnsFalse() {
        Tile tile = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.GREEN, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile2);
        handOfTiles.add(tile);
        Hand hand = new Hand(handOfTiles);
        assertFalse(hand.checkIfSequenceOfThreeIsPresentInHand(0, 0));
    }

    @Test
    public void testIfHandHasArraysOfFalseSameSizeAsArrayListWhenCreated() {
        Tile tile = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.GREEN, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile2);
        handOfTiles.add(tile);
        Hand hand = new Hand(handOfTiles);
        assertEquals(2, hand.getValidatedHandArray().length);
        assertFalse(hand.getValidatedHandArray()[0]);
        assertFalse(hand.getValidatedHandArray()[1]);
    }

    @Test
    public void testIfHandSizeIsZeroValidatedHandArrayOfLengthZeroIsCreated() {
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        Hand hand = new Hand(handOfTiles);
        assertEquals(0, hand.getValidatedHandArray().length);
    }

    @Test
    public void testWhenSequenceIsPresentIfIndexMatchingToSequenceIsSetToTrueInValidatedHandArray() {
        ArrayList<Tile> handOfTiles = createMockHandForSequencetesting();
        Hand hand = new Hand(handOfTiles);
        hand.checkIfSequenceOfThreeIsPresentInHand(0, 0);
        assertTrue(hand.checkIfSequenceOfThreeIsPresentInHand(0, 0));
        assertTrue(hand.getValidatedHandArray()[0]);
        assertTrue(hand.getValidatedHandArray()[3]);
        assertTrue(hand.getValidatedHandArray()[10]);
        assertFalse(hand.getValidatedHandArray()[2]);
    }

    @Test
    public void testIfTwoSequenceCanBeDetectedInMockHand() {
        ArrayList<Tile> handOfTiles = createMockHandForSequencetesting();
        Hand hand = new Hand(handOfTiles);
        assertEquals(2, hand.searchForAllSequencesInHandAndUpdateValidatedHandArray());
    }

    @Test
    public void testIfWhenTripletIsFoundAllTileOfTripletAreSwitchedToTrue() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.WEST, Suit.WIND);
        Tile tile3 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile4 = new Tile(Value.FOUR, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile4);
        Hand hand = new Hand(handOfTiles);
        hand.checkIfTripletOfValue(Value.SOUTH, Suit.WIND);
        hand.checkIfTripletOfValue(Value.GREEN, Suit.DRAGON);
        hand.checkIfTripletOfValue(Value.FOUR, Suit.BAMBOO);
        assertTrue(hand.getValidatedHandArray()[0]);
        assertTrue(hand.getValidatedHandArray()[6]);
        assertFalse(hand.getValidatedHandArray()[9]);
        assertFalse(hand.getValidatedHandArray()[4]);
    }

    @Test
    public void testIfAllTripletsAreDetectedAndSwitchedToTrue() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.WEST, Suit.WIND);
        Tile tile3 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile4 = new Tile(Value.FOUR, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile3);
        handOfTiles.add(tile4);
        Hand hand = new Hand(handOfTiles);
        int tripletCount = hand.checkForAllTripletsInHandAndUpdateValidatedHandArray();
        assertTrue(hand.getValidatedHandArray()[0]);
        assertTrue(hand.getValidatedHandArray()[4]);
        assertTrue(hand.getValidatedHandArray()[6]);
        assertFalse(hand.getValidatedHandArray()[9]);
        assertEquals(3, tripletCount);
    }

    @Test
    public void testIfReturnsTrueWhenTwoOfTheSameTilesArePresent() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.GREEN, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfSpecificTileIsPresentOnlyTwice(tile, 0, 0));
        assertFalse(hand.checkIfSpecificTileIsPresentOnlyTwice(tile2, 0, 0));
    }

    @Test
    public void testIfHandOfSevenPairsReturnsTrue() {
        ArrayList<Tile> handOfTiles = createMockHandForSevenPairs();
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.validateHandOfTiles());
    }

    @Test
    public void testIfMoreThanOnePairReturnsTrue() {
        ArrayList<Tile> handOfTiles = createInvalidMockHandForSevenPairs();
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfAPairIsPresentMoreThanOnce(hand.getHand().get(0), 0, 0));
    }

    @Test
    public void testIfNoDuplicatesOfPairsArePresentReturnsFalse() {
        Tile tile = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile2 = new Tile(Value.GREEN, Suit.DRAGON);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile);
        handOfTiles.add(tile2);
        handOfTiles.add(tile);
        Hand hand = new Hand(handOfTiles);
        assertFalse(hand.checkIfAPairIsPresentMoreThanOnce(tile, 0, 0));
    }

    @Test
    public void testIfTilePreviousEqualsTheNextTileReturnsTrue() {
        Tile tile1 = new Tile(Value.TWO, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.THREE, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile2);
        handOfTiles.add(tile1);
        handOfTiles.add(tile2);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.checkIfTileToCompareHasValueBeforeFirstTile(0, 1));
        assertFalse(hand.checkIfTileToCompareHasValueBeforeFirstTile(1, 2));
    }

    @Test
    public void testIfReturnsTrueWhenSequenceIsPresentButInWrongOrder() {
        Tile tile1 = new Tile(Value.TWO, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.THREE, Suit.BAMBOO);
        ArrayList<Tile> handOfTiles = new ArrayList<Tile>();
        handOfTiles.add(tile1);
        handOfTiles.add(tile2);
        handOfTiles.add(tile3);
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.handleCheckingIfSecondAndThirdTileAreSeqeunceOfFirstTile(0, 0));
    }

    @Test
    public void testIfAHandOfThirteenOrphansReturnsTrue() {
        ArrayList<Tile> handOfTiles = createMockHandForThirteenOrphans();
        Hand hand = new Hand(handOfTiles);
        assertTrue(hand.validateHandOfTiles());
    }

    public ArrayList<Tile> createMockHandForSequencetesting() {
        ArrayList<Tile> handList = new ArrayList<Tile>();
        Tile tile1 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.FOUR, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile4 = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile5 = new Tile(Value.FIVE, Suit.CHARACTER);
        Tile tile6 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile7 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile8 = new Tile(Value.EAST, Suit.WIND);
        Tile tile9 = new Tile(Value.WEST, Suit.WIND);
        Tile tile10 = new Tile(Value.NORTH, Suit.WIND);
        Tile tile11 = new Tile(Value.TWO, Suit.BAMBOO);
        Tile tile12 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile13 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile14 = new Tile(Value.SIX, Suit.CHARACTER);
        handList.add(tile1);
        handList.add(tile2);
        handList.add(tile3);
        handList.add(tile4);
        handList.add(tile5);
        handList.add(tile6);
        handList.add(tile7);
        handList.add(tile8);
        handList.add(tile9);
        handList.add(tile10);
        handList.add(tile11);
        handList.add(tile12);
        handList.add(tile13);
        handList.add(tile14);
        return handList;
    }

    public ArrayList<Tile> createMockHandForPongChowandPair() {
        ArrayList<Tile> handList = new ArrayList<Tile>();
        Tile tile1 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.TWO, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile4 = new Tile(Value.FIVE, Suit.BAMBOO);
        Tile tile5 = new Tile(Value.SIX, Suit.CHARACTER);
        Tile tile6 = new Tile(Value.SEVEN, Suit.BAMBOO);
        Tile tile7 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile8 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile9 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile10 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile11 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile12 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile13 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile14 = new Tile(Value.GREEN, Suit.DRAGON);
        handList.add(tile1);
        handList.add(tile2);
        handList.add(tile3);
        handList.add(tile4);
        handList.add(tile5);
        handList.add(tile6);
        handList.add(tile7);
        handList.add(tile8);
        handList.add(tile9);
        handList.add(tile10);
        handList.add(tile11);
        handList.add(tile12);
        handList.add(tile13);
        handList.add(tile14);
        return handList;
    }

    public ArrayList<Tile> createMockHandForSevenPairs() {
        ArrayList<Tile> handList = new ArrayList<Tile>();
        Tile tile1 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.FIVE, Suit.BAMBOO);
        Tile tile4 = new Tile(Value.FIVE, Suit.BAMBOO);
        Tile tile5 = new Tile(Value.SEVEN, Suit.BAMBOO);
        Tile tile6 = new Tile(Value.SEVEN, Suit.BAMBOO);
        Tile tile7 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile8 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile9 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile10 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile11 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile12 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile13 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile14 = new Tile(Value.GREEN, Suit.DRAGON);
        handList.add(tile1);
        handList.add(tile2);
        handList.add(tile3);
        handList.add(tile4);
        handList.add(tile5);
        handList.add(tile6);
        handList.add(tile7);
        handList.add(tile8);
        handList.add(tile9);
        handList.add(tile10);
        handList.add(tile11);
        handList.add(tile12);
        handList.add(tile13);
        handList.add(tile14);
        return handList;
    }

    public ArrayList<Tile> createInvalidMockHandForSevenPairs() {
        ArrayList<Tile> handList = new ArrayList<Tile>();
        Tile tile1 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile4 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile5 = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile6 = new Tile(Value.THREE, Suit.BAMBOO);
        Tile tile7 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile8 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile9 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile10 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile11 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile12 = new Tile(Value.EIGHT, Suit.BAMBOO);
        Tile tile13 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile14 = new Tile(Value.GREEN, Suit.DRAGON);
        handList.add(tile1);
        handList.add(tile2);
        handList.add(tile3);
        handList.add(tile4);
        handList.add(tile5);
        handList.add(tile6);
        handList.add(tile7);
        handList.add(tile8);
        handList.add(tile9);
        handList.add(tile10);
        handList.add(tile11);
        handList.add(tile12);
        handList.add(tile13);
        handList.add(tile14);
        return handList;
    }

    public ArrayList<Tile> createMockHandForThirteenOrphans() {
        ArrayList<Tile> handList = new ArrayList<Tile>();
        Tile tile1 = new Tile(Value.ONE, Suit.BAMBOO);
        Tile tile2 = new Tile(Value.NINE, Suit.BAMBOO);
        Tile tile3 = new Tile(Value.ONE, Suit.CHARACTER);
        Tile tile4 = new Tile(Value.NINE, Suit.CHARACTER);
        Tile tile5 = new Tile(Value.ONE, Suit.CIRCLE);
        Tile tile6 = new Tile(Value.NINE, Suit.CIRCLE);
        Tile tile7 = new Tile(Value.WHITE, Suit.DRAGON);
        Tile tile8 = new Tile(Value.GREEN, Suit.DRAGON);
        Tile tile9 = new Tile(Value.RED, Suit.DRAGON);
        Tile tile10 = new Tile(Value.NORTH, Suit.WIND);
        Tile tile11 = new Tile(Value.SOUTH, Suit.WIND);
        Tile tile12 = new Tile(Value.EAST, Suit.WIND);
        Tile tile13 = new Tile(Value.WEST, Suit.WIND);
        Tile tile14 = new Tile(Value.GREEN, Suit.DRAGON);
        handList.add(tile1);
        handList.add(tile2);
        handList.add(tile3);
        handList.add(tile4);
        handList.add(tile5);
        handList.add(tile6);
        handList.add(tile7);
        handList.add(tile8);
        handList.add(tile9);
        handList.add(tile10);
        handList.add(tile11);
        handList.add(tile12);
        handList.add(tile13);
        handList.add(tile14);
        return handList;
    }

}
