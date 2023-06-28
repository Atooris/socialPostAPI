package pl.atooris.SocialPostAPI.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.atooris.SocialPostAPI.entity.Role;
import pl.atooris.SocialPostAPI.security.SecurityConstants;
import pl.atooris.SocialPostAPI.service.UserServiceImpl;

import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION);

        if(header == null || !header.startsWith(SecurityConstants.BEARER)){
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().startsWith("/h2")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
        Set<Role> userRoles = userService.getUser(user).getRoles();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userRoles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
