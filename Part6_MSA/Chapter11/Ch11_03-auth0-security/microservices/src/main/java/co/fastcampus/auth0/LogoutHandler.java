package co.fastcampus.auth0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Needed to perform SSO logout with Auth0. By default, Spring will clear the {@linkplain org.springframework.security.core.context.SecurityContext}
 * and the session. This controller will also log users out of Auth0 by calling the
 * <a href="https://auth0.com/docs/api/authentication?javascript&_ga=2.220855071.683371550.1592511441-1771410918.1592511441#logout">Auth0 logout endpoint</a>.
 */
@Controller
public class LogoutHandler extends SecurityContextLogoutHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ClientRegistrationRepository clientRegistrationRepository;

    /**
     * Create a new instance with a {@code ClientRegistrationRepository}, so that we can look up information about the
     * configured provider to call the Auth0 logout endpoint. Called by the Spring framework.
     *
     * @param clientRegistrationRepository the {@code ClientRegistrationRepository} for this application.
     */
    @Autowired
    public LogoutHandler(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    /**
     * Delegates to {@linkplain SecurityContextLogoutHandler} to log the user out of the application, and then logs
     * the user out of Auth0.
     *
     * @param httpServletRequest the request.
     * @param httpServletResponse the response.
     * @param authentication the current authentication.
     */
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       Authentication authentication) {

        // Invalidate the session and clear the security context
        super.logout(httpServletRequest, httpServletResponse, authentication);

        // Build the URL to log the user out of Auth0 and redirect them to the home page.
        // URL will look like https://YOUR-DOMAIN/v2/logout?clientId=YOUR-CLIENT-ID&returnTo=http://localhost:3000
        String issuer = (String) getClientRegistration().getProviderDetails().getConfigurationMetadata().get("issuer");
        String clientId = getClientRegistration().getClientId();
        String returnTo = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

        String logoutUrl = UriComponentsBuilder
                .fromHttpUrl(issuer + "v2/logout?client_id={clientId}&returnTo={returnTo}")
                .encode()
                .buildAndExpand(clientId, returnTo)
                .toUriString();

        log.info("Will attempt to redirect to logout URL: {}", logoutUrl);
        try {
            httpServletResponse.sendRedirect(logoutUrl);
        } catch (IOException ioe) {
            log.error("Error redirecting to logout URL", ioe);
        }
    }

    /**
     * Gets the Spring ClientRegistration, which we use to get the registered client ID and issuer for building the
     * {@code returnTo} query parameter when calling the Auth0 logout API.
     *
     * @return the {@code ClientRegistration} for this application.
     */
    private ClientRegistration getClientRegistration() {
        return this.clientRegistrationRepository.findByRegistrationId("auth0");
    }
}
