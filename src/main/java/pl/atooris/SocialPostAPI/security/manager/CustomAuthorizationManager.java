package pl.atooris.SocialPostAPI.security.manager;

import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import pl.atooris.SocialPostAPI.security.SecurityConstants;
import pl.atooris.SocialPostAPI.service.PostService;
import pl.atooris.SocialPostAPI.service.UserService;

import java.util.function.Supplier;


@Component
@AllArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    UserService userService;
    PostService postService;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        boolean isAdmin = authentication.get().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(SecurityConstants.ROLE_ADMIN));

        String requestEndpoint = object.getRequest().getRequestURI();

        // Pobierz userId i postId z dynamicznych segmentów ścieżki URL
        String[] pathSegments = requestEndpoint.split("/");
        String userId = pathSegments[2];
        String postId = pathSegments[4];
        String expectedEndpoint = "/user/" + userId + "/post/" + postId;

        if(requestEndpoint.equals(expectedEndpoint) && object.getRequest().getMethod().equals("DELETE")){
            boolean isOwner = postService.isOwnerOfPost(Long.parseLong(postId), Long.parseLong(userId));
            return isOwner || isAdmin
                    ? new AuthorizationDecision(true) : new AuthorizationDecision(false);
        }
        return null;
    }
}
