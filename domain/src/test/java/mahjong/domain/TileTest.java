package mahjong.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mahjong.domain.Tile.Suit;
import mahjong.domain.Tile.Type;
import mahjong.domain.Tile.Value;

public class TileTest {

    @Test
    public void testIfValueOfSixIsSet() {
        Tile tile = new Tile();
        tile.setValue(Value.SIX);
        assertEquals(Value.SIX, tile.getValue());
    }

    @Test
    public void testIfSuitOfBambooIsSet() {
        Tile tile = new Tile();
        tile.setSuit(Suit.BAMBOO);
        assertEquals(Suit.BAMBOO, tile.getSuit());
    }

    @Test
    public void testIfTileWithValueOneHasTypeTerminal() {
        Tile tile = new Tile(Value.ONE, Suit.BAMBOO);
        assertEquals(Type.TERMINAL, tile.getType());
    }

    @Test
    public void testIfTileBambooTwoHasTypeInside() {
        Tile tile = new Tile(Value.TWO, Suit.BAMBOO);
        assertEquals(Type.INSIDE, tile.getType());
    }

    @Test
    public void testIfTileBambooNineHasTypeTerminal() {
        Tile tile = new Tile(Value.NINE, Suit.BAMBOO);
        assertEquals(Type.TERMINAL, tile.getType());
    }

    @Test
    public void testIFNorthWindHasTypeHonour() {
        Tile tile = new Tile(Value.NORTH, Suit.WIND);
        assertEquals(Type.HONOUR, tile.getType());
    }

    @Test
    public void testIFGreenDragonHasTypeHonour() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertEquals(Type.HONOUR, tile.getType());
    }

    @Test
    public void testIfReturnsFalseWhenDragonOne() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertFalse(tile.validateNumberValue(Value.ONE, Suit.DRAGON));
    }

    @Test
    public void testIfReturnsFalseWhenWindOne() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertFalse(tile.validateNumberValue(Value.ONE, Suit.WIND));
    }

    @Test
    public void testIfReturnsTrueWhenBambooNumber() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertTrue(tile.validateNumberValue(Value.ONE, Suit.BAMBOO));
        assertTrue(tile.validateNumberValue(Value.TWO, Suit.BAMBOO));
    }

    @Test
    public void testIfReturnsTrueWhenTileHasDragonAndDragonValue() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertFalse(tile.validateHonourValue(Value.ONE, Suit.DRAGON));
        assertTrue(tile.validateHonourValue(Value.WHITE, Suit.DRAGON));
        assertFalse(tile.validateHonourValue(Value.WHITE, Suit.WIND));
    }

    @Test
    public void testIfReturnsTrueWhenTileHasWindAndWindValue() {
        Tile tile = new Tile(Value.GREEN, Suit.DRAGON);
        assertFalse(tile.validateHonourValue(Value.ONE, Suit.WIND));
        assertTrue(tile.validateHonourValue(Value.WEST, Suit.WIND));
        assertFalse(tile.validateHonourValue(Value.WEST, Suit.DRAGON));
    }

}
