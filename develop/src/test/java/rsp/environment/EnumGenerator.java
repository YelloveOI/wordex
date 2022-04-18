package rsp.environment;

import rsp.enums.Language;
import rsp.enums.Role;

import java.util.Random;

public class EnumGenerator {

    private static final Random RAND = new Random();

    public static Role generateRole() {
        return Role.values()[RAND.nextInt(Role.values().length)];
    }

    public static Language generateLanguage() {
        return Language.values()[RAND.nextInt(Language.values().length)];
    }
}
