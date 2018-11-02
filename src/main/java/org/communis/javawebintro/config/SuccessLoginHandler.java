package org.communis.javawebintro.config;

import org.communis.javawebintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс для обработки события удачной аутентификации
 */
public class SuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private ClassicAuthenticationProvider classicAuthenticationProvider;
    @Autowired
    private SessionRegistry sessionRegistry;

    public SuccessLoginHandler(String defaultTargetUrl) {
        super(defaultTargetUrl);
    }

    /**
     * Обработчик события успешной авторизации.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            UserDetailsImpl userDetailsImpl;
            try {
                userDetailsImpl = (UserDetailsImpl) userService.loadUserByUsername(principal.getUsername());
                authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl,
                        authentication.getCredentials(), userDetailsImpl.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String sessionId = sessionRegistry.getAllSessions(principal, false).get(0).getSessionId();

                sessionRegistry.getAllSessions(principal, false).forEach(SessionInformation::expireNow);

                sessionRegistry.registerNewSession(sessionId, userDetailsImpl);
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        }

        userService.updateLastOnlineDate(((UserDetailsImpl) authentication.getPrincipal()).getUser().getId());

        response.setStatus(HttpStatus.OK.value());
    }
}
