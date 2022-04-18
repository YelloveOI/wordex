package rsp.environment;

import rsp.enums.Language;
import rsp.model.*;

import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public Card generateCustomCard(String definition, String term, boolean isPublic, Language languageFrom, Language languageTo) {
        return new Card(definition, term, isPublic, languageFrom, languageTo);
    }

    public Card generateRandomCard(boolean isPublic) {
        final Card card = new Card();

        card.setDefinition("Definition"+randomInt());
        card.setTerm("term"+randomInt());
        card.setPublic(isPublic);
        card.setLanguageFrom(EnumGenerator.generateLanguage());
        card.setLanguageTo(EnumGenerator.generateLanguage());

        return card;
    }


}
