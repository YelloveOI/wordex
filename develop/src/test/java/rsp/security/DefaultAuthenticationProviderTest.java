// TODO: Fix when new security is implemented
//package rsp.security;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import rsp.environment.Generator;
//import rsp.model.User;
//import rsp.service.interfaces.UserService;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class DefaultAuthenticationProviderTest {
//
//    @Autowired
//    private UserService sut;
//
//    @Autowired
//    private DefaultAuthenticationProvider provider;
//
//    private final User user = Generator.generateRandomUser();
//    private final String rawPassword = user.getPassword();
//
//    @BeforeEach
//    public void setUp() {
//        sut.save(user);
//        SecurityContextHolder.setContext(new SecurityContextImpl());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        SecurityContextHolder.setContext(new SecurityContextImpl());
//    }
//
//    @Test
//    public void authentication_validUserCredentials_SetsSecurityContext() {
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword);
//        final SecurityContext context = SecurityContextHolder.getContext();
//        assertNull(context.getAuthentication());
//
//        final Authentication result = provider.authenticate(auth);
//        assertNotNull(result);
//        assertTrue(result.isAuthenticated());
//        assertNotNull(SecurityContextHolder.getContext());
//
//        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        assertEquals(user.getUsername(), details.getUsername());
//        assertTrue(result.isAuthenticated());
//    }
//
//    @Test
//    public void authentication_unknownUser_throwsException(){
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername() + "13", rawPassword);
//        final SecurityContext context = SecurityContextHolder.getContext();
//        assertNull(context.getAuthentication());
//
//        assertThrows(UsernameNotFoundException.class, ()->provider.authenticate(auth));
//
//        assertNull(context.getAuthentication());
//    }
//
//    @Test
//    public void authentication_invalidCredentials_throwsException(){
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(),"invalid" + rawPassword);
//        final SecurityContext context = SecurityContextHolder.getContext();
//        assertNull(context.getAuthentication());
//
//        assertThrows(BadCredentialsException.class, ()->provider.authenticate(auth));
//
//        assertNull(context.getAuthentication());
//    }
//
//    @Test
//    public void supportsUsernameAndPasswordAuthentication() {
//        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
//    }
//
//    @Test
//    public void successfulLoginErasesPasswordInSecurityContextUser() {
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword);
//        provider.authenticate(auth);
//
//        assertNotNull(SecurityContextHolder.getContext());
//        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        assertNull(details.getUser().getPassword());
//    }
//
//
//}
