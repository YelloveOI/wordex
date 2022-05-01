package rsp.security.filters;

import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import rsp.exception.InvalidJwtTokenException;
import rsp.repo.UserRepo;
import rsp.security.SecurityConstants;
import rsp.security.UserDetails;
import rsp.security.service.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component @Slf4j @RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    final private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Running Authorization filter");

        if (req.getRequestURI().startsWith("/auth")) {
            filterChain.doFilter(req, res);
            return;
        }

        try {
            String jwtToken = parseTokenFromHeader(req).orElseThrow(InvalidJwtTokenException::new);

            String username = JWT.decode(jwtToken).getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(req, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Malformed or expirated token.");
            return;
        }

        filterChain.doFilter(req, res);
    }

    /**
     * @param req HTTP request object with headers.
     * @return parsed JWT token or empty if non-existent.
     */
    private Optional<String> parseTokenFromHeader(HttpServletRequest req) {
        String authorizationHeader = req.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return Optional.of(
                    authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length())
            );
        }

        return Optional.empty();
    }
}
