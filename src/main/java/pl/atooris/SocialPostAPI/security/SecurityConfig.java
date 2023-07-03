package pl.atooris.SocialPostAPI.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.atooris.SocialPostAPI.security.filter.AuthenticationFilter;
import pl.atooris.SocialPostAPI.security.filter.ExceptionHandlerRequest;
import pl.atooris.SocialPostAPI.security.filter.JWTAuthenticationFilter;
import pl.atooris.SocialPostAPI.security.manager.CustomAuthenticationManager;
import pl.atooris.SocialPostAPI.security.manager.CustomAuthorizationManager;
import pl.atooris.SocialPostAPI.service.PostServiceImpl;
import pl.atooris.SocialPostAPI.service.UserServiceImpl;

import static pl.atooris.SocialPostAPI.security.SecurityConstants.*;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/user/*/confirm/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/user/*/role/*")).hasAuthority(ROLE_ADMIN)
                .requestMatchers(new AntPathRequestMatcher("admin/user/*/role/*")).hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET).hasAnyAuthority(ROLE_USER, ROLE_ADMIN, ROLE_GUEST)
                .requestMatchers(HttpMethod.POST).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                .requestMatchers(HttpMethod.PATCH).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                .requestMatchers(HttpMethod.PUT).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE).access(authorizationManager())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerRequest(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthenticationFilter(userService), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager() {
        return new CustomAuthorizationManager(userService, postService);
    }

//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
//        return roleHierarchy;
//    }

}
