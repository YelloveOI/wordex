package rsp.environment;

import rsp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListGenerators {

    public static List<Card> generateCards() {
        List<Card> cards = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            cards.add(Generator.generateRandomCard());
        }

        return cards;
    }

    public static List<Deck> generateDecks() {
        List<Deck> decks = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            decks.add(Generator.generateRandomFullDeck());
        }

        return decks;
    }

    public static List<User> generateListOfStudents() {
        List<User> students = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            students.add(Generator.generateStudent());
        }

        return students;
    }

    public static List<User> generateListOfTeachers() {
        List<User> students = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            students.add(Generator.generateTeacher());
        }

        return students;
    }

    public static List<User> generateListOfModerators() {
        List<User> students = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < random.nextInt(10) + 1; i++) {
            students.add(Generator.generateModerator());
        }

        return students;
    }
}
