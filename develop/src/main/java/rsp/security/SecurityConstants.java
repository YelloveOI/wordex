package rsp.security;

public class SecurityConstants {

    private SecurityConstants() {
        throw new AssertionError();
    }

    public static final String SESSION_COOKIE_NAME = "WORDEX_JSESSIONID";

    public static final String REMEMBER_ME_COOKIE_NAME = "remember-me";

    public static final String USERNAME_PARAM = "username";

    public static final String PASSWORD_PARAM = "password";

    public static final String SECURITY_CHECK_URI = "/security_check_login";

    public static final String LOGOUT_URI = "/security_logout";

    public static final String COOKIE_URI = "/";

    public static final int SESSION_TIMEOUT = 30 * 60;
}