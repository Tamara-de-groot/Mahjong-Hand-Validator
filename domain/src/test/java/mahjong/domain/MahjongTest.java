package mahjong.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class MahjongTest {

    @Test
    public void testIfFileIsReadAndHandsAreAddedToList() {
        File file = new File("./resources/mahjong_example_hands");
        File file2 = new File("./resources/mahjong_nine_gates");
        Mahjong mahjong1 = new Mahjong(file);
        Mahjong mahjong2 = new Mahjong(file2);
        assertEquals("🀀 🀀 🀀 🀓 🀔 🀕 🀅 🀅 🀜 🀝 🀝 🀞 🀞 🀟", mahjong1.getListOfAllMahjongHands().get(0));
        assertEquals("🀡 🀡 🀁 🀁 🀕 🀕 🀚 🀚 🀌 🀌 🀖 🀖 🀗 🀗", mahjong1.getListOfAllMahjongHands().get(1));
        assertEquals("🀐 🀐 🀐 🀐 🀑 🀒 🀓 🀔 🀕 🀖 🀗 🀘 🀘 🀘", mahjong2.getListOfAllMahjongHands().get(0));
    }

    @Test
    public void testIfAllHandConsistsOfSixHands() {
        File file = new File("./resources/mahjong_example_hands");
        Mahjong mahjong = new Mahjong(file);
        assertEquals(6, mahjong.getListOfAllHands().size());
        assertEquals(Hand.class, mahjong.getListOfAllHands().get(2).getClass());
    }

    @Test
    public void testIfAllHandsAreValidForNineGates() {
        File exampleHands = new File("./resources/mahjong_nine_gates");
        Mahjong mahjong = new Mahjong(exampleHands);
        assertTrue(mahjong.validateAnArrayListOfHands());
    }

    @Test
    public void testIfAllHandsAreValidForExampeHands() {
        File exampleHands = new File("./resources/mahjong_example_hands");
        Mahjong mahjong = new Mahjong(exampleHands);
        assertTrue(mahjong.validateAnArrayListOfHands());
    }

    @Test
    public void testIfAllHandsAreInvalidForUnlegitHands() {
        File exampleHands = new File("./resources/mahjong_unlegit_hands");
        Mahjong mahjong = new Mahjong(exampleHands);
        assertFalse(mahjong.validateAnArrayListOfHands());
    }

}
