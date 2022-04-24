package rsp.model;

import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void addDeck_AddsADeckToAnEmptyUser() {
        final User user = Generator.generateRandomUser();
        final Deck deck = Generator.generateRandomFullDeck();

        user.addDeck(deck);

        Assertions.assertEquals(1, user.getDecks().size());
    }

    @Test
    public void addDeck_AddsADeckToAFullUser() {
        final User user = Generator.generateRandomUserWithDecks();
        final Deck deck = Generator.generateRandomFullDeck();
        Integer expected = user.getDecks().size() + 1;

        user.addDeck(deck);

        Assertions.assertEquals(expected, user.getDecks().size());
    }

    @Test
    public void removeDeck_RemovesADeckFromAUser() {
        final User user = Generator.generateRandomUser();
        final Deck deck = Generator.generateRandomFullDeck();
        user.addDeck(deck);

        user.removeDeck(deck);

        Assertions.assertEquals(0, user.getDecks().size());
    }
}
