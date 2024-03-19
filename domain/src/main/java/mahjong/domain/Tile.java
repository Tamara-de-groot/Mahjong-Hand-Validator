package mahjong.domain;

import java.util.EnumSet;
import java.util.Optional;

public class Tile {

    private Value value;
    private Suit suit;
    private Type type;

    public enum Value {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EAST, SOUTH, WEST, NORTH,
        RED, GREEN, WHITE;

        public Optional<Value> next() {
            switch (this) {
                case ONE:
                    return Optional.of(TWO);
                case TWO:
                    return Optional.of(THREE);
                case THREE:
                    return Optional.of(FOUR);
                case FOUR:
                    return Optional.of(FIVE);
                case FIVE:
                    return Optional.of(SIX);
                case SIX:
                    return Optional.of(SEVEN);
                case SEVEN:
                    return Optional.of(EIGHT);
                case EIGHT:
                    return Optional.of(NINE);
                default:
                    return Optional.empty();
            }
        }

        public Optional<Value> previous() {
            switch (this) {
                case TWO:
                    return Optional.of(ONE);
                case THREE:
                    return Optional.of(TWO);
                case FOUR:
                    return Optional.of(THREE);
                case FIVE:
                    return Optional.of(FOUR);
                case SIX:
                    return Optional.of(FIVE);
                case SEVEN:
                    return Optional.of(SIX);
                case EIGHT:
                    return Optional.of(SEVEN);
                case NINE:
                    return Optional.of(EIGHT);
                default:
                    return Optional.empty();
            }
        }
    }

    public enum Suit {
        CHARACTER, BAMBOO, CIRCLE, WIND, DRAGON
    }

    public enum Type {
        TERMINAL, HONOUR, INSIDE
    }

    public EnumSet<Value> NUMBERS = EnumSet.of(Tile.Value.ONE, Tile.Value.TWO, Tile.Value.THREE,
            Tile.Value.FOUR, Tile.Value.FIVE, Tile.Value.SIX,
            Tile.Value.SEVEN, Tile.Value.EIGHT, Tile.Value.NINE);

    public EnumSet<Value> WIND_VALUES = EnumSet.of(Tile.Value.EAST, Tile.Value.WEST, Tile.Value.SOUTH,
            Tile.Value.NORTH);

    public EnumSet<Value> DRAGON_VALUES = EnumSet.of(Tile.Value.RED, Tile.Value.GREEN, Tile.Value.WHITE);

    public Tile() {
    }

    public Tile(Value value, Suit suit) {
        if (validateTile(value, suit)) {
            this.value = value;
            this.suit = suit;
            this.assignType(value, suit);
        } else {
            throw new IllegalArgumentException("This tile is invalid");
        }
    }

    public Value getValue() {
        return this.value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Type getType() {
        return this.type;
    }

    private void assignType(Value value, Suit suit) {
        if (value == Value.ONE || value == Value.NINE) {
            this.type = Type.TERMINAL;
        } else if (suit == Suit.WIND || suit == Suit.DRAGON) {
            this.type = Type.HONOUR;
        } else {
            this.type = Type.INSIDE;
        }
    }

    public boolean validateNumberValue(Value value, Suit suit) {
        if (suit == Suit.DRAGON && NUMBERS.contains(value)) {
            return false;
        } else if (suit == Suit.WIND && NUMBERS.contains(value)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateHonourValue(Value value, Suit suit) {
        if (suit == Suit.DRAGON && DRAGON_VALUES.contains(value)) {
            return true;
        } else if (suit == Suit.WIND && WIND_VALUES.contains(value)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateTile(Value value, Suit suit) {
        if (this.validateHonourValue(value, suit)) {
            return true;
        } else if (this.validateNumberValue(value, suit)) {
            return true;
        }
        return false;
    }

}
