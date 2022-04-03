package rsp.enums;

public enum Role {
    ADMIN("ROLE_ADMINISTRATOR"), USER("ROLE_USER"), STUDENT("ROLE_STUDENT"), SCHOOL_REPRESENTATIVE("ROLE_SCHOOL_REPRESENTATIVE")
    , PREMIUM_USER("ROLE_PREMIUM_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
