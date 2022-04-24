package rsp.model;

import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTest {

    @Test
    public void addCard_AddsACardToAnEmptyDeck() {
        final Deck deck = Generator.generateRandomEmptyDeck();
        final Card card = Generator.generateRandomCard();

        deck.addCard(card);

        Assertions.assertEquals(1, deck.getCards().size());
    }

    @Test
    public void addCard_AddsACardToAFullDeck() {
        final Deck deck = Generator.generateRandomFullDeck();
        final Card card = Generator.generateRandomCard();
        Integer expected = deck.getCards().size() + 1;

        deck.addCard(card);

        Assertions.assertEquals(expected, deck.getCards().size());
    }

    @Test
    public void removeCard_RemovesACardFromADeck() {
        final Deck deck = Generator.generateRandomEmptyDeck();
        final Card card = Generator.generateRandomCard();
        deck.addCard(card);

        deck.removeCard(card);

        Assertions.assertEquals(0, deck.getCards().size());
    }
}
