package co.fastcampus.auth0;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LogoutHandler logoutHandler;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               // allow all users to access the home pages and the static images directory
               .mvcMatchers("/", "/images/**").permitAll()
               // all other requests must be authenticated
               .anyRequest().authenticated()
           .and().oauth2Login()
           .and().logout()
               // handle logout requests at /logout path
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               // customize logout handler to log out of Auth0
               .addLogoutHandler(logoutHandler);
    }

// If using HS256, create a Bean to specify the HS256 should be used. By default, RS256 will be used.
//    @Bean
//    public JwtDecoderFactory<ClientRegistration> idTokenDecoderFactory() {
//        OidcIdTokenDecoderFactory idTokenDecoderFactory = new OidcIdTokenDecoderFactory();
//        idTokenDecoderFactory.setJwsAlgorithmResolver(clientRegistration -> MacAlgorithm.HS256);
//        return idTokenDecoderFactory;
//    }
}
