package rsp.security;

import com.auth0.jwt.JWT;
import org.jetbrains.annotations.NotNull;
import rsp.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Date;

public class SecurityUtils {

    public static String jwtGenerateAccessToken(@NotNull User user) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.JWT_ACCESS_TOKEN_EXPIRATION_TIME))
                .withSubject(user.getUsername())
                .sign(SecurityConstants.JWT_ALGORITHM);
    }

    public static User getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();

        assert context != null;

        final UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();

        return userDetails.getUser();
    }

//    public static UserDetails getCurrentUserDetails() {
//        final SecurityContext context = SecurityContextHolder.getContext();
//        if (context.getAuthentication() != null && context.getAuthentication().getDetails() instanceof UserDetails) {
//            return (UserDetails) context.getAuthentication().getDetails();
//        } else {
//            return null;
//        }
//    }

//    public static AuthenticationToken setCurrentUser(UserDetails userDetails) {
//        final AuthenticationToken token = new AuthenticationToken(userDetails.getAuthorities(), userDetails);
//        token.setAuthenticated(true);
//
//        final SecurityContext context = new SecurityContextImpl();
//        context.setAuthentication(token);
//        SecurityContextHolder.setContext(context);
//        return token;
//    }

//    public static boolean isAuthenticatedAnonymously() {
//        return getCurrentUserDetails() == null;
//    }
}