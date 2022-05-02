package rsp.environment;

import rsp.enums.Language;
import rsp.enums.Role;
import rsp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static Card generateRandomCard() {
        final Card card = new Card();

        card.setDefinition("Definition"+randomInt());
        card.setTerm("Term"+randomInt());

        return card;
    }

    public static Deck generateEmptyDeck(boolean isConfigurable, boolean isPrivate) {
        final Deck deck = new Deck();

        deck.setDescription("Description"+randomInt());
        deck.setConfigurable(isConfigurable);
        deck.setPrivate(isPrivate);
        deck.setName("Name"+randomInt());
        deck.setLanguageFrom(EnumGenerator.generateLanguage());
        deck.setLanguageTo(EnumGenerator.generateLanguage());

        return deck;
    }

    public static Deck generateFullDeck(boolean isConfigurable, boolean isPrivate,
                                        Language languageFrom, Language languageTo) {
        final Deck deck = generateEmptyDeck(isConfigurable, isPrivate);

        deck.setLanguageFrom(languageFrom);
        deck.setLanguageTo(languageTo);

        deck.setCards(ListGenerators.generateCards());

        return deck;
    }

    public static Deck generateRandomEmptyDeck() {
        final Deck deck = generateEmptyDeck(randomBoolean(), randomBoolean());

        deck.setLanguageFrom(EnumGenerator.generateLanguage());
        deck.setLanguageTo(EnumGenerator.generateLanguage());

        return deck;
    }

    public static Deck generateRandomFullDeck() {
        final Deck deck = generateEmptyDeck(randomBoolean(), randomBoolean());

        deck.setLanguageFrom(EnumGenerator.generateLanguage());
        deck.setLanguageTo(EnumGenerator.generateLanguage());

        deck.setCards(ListGenerators.generateCards());

        return deck;
    }

    public static School generateEmptySchool() {
        final School school = new School();

        school.setName("Name"+randomInt());
        school.setCity("City"+randomInt());

        return school;
    }

    public static School generateFullSchool() {
        final School school = generateEmptySchool();

        school.setStudents(ListGenerators.generateListOfStudents());
        school.setTeachers(ListGenerators.generateListOfTeachers());
        school.setModerators(ListGenerators.generateListOfModerators());

        return school;
    }

    public static User generateRandomUser() {
        final User user = new User();

        user.setId(randomInt());
        user.setUsername("Username"+randomInt());
        user.setEmail("Email"+randomInt() + "@gmail.com");
        user.setPassword(Integer.toString(randomInt())+ "@?5aaHH");

        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        return user;
    }

    //Deprecated
//    public static User generateRandomUserWithDecks() {
//        final User user = generateRandomUser();
//
//        user.setDecks(ListGenerators.generateDecks());
//
//        return user;
//    }

    public static User generateStudent() {
        final User user = generateRandomUser();

        user.addRole(Role.STUDENT);

        return user;
    }

    public static User generateTeacher() {
        final User user = generateRandomUser();

        user.addRole(Role.SCHOOL_REPRESENTATIVE);

        return user;
    }

    public static User generateModerator() {
        final User user = generateRandomUser();

        user.addRole(Role.ADMIN);

        return user;
    }
}
