package hac.ex4.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The department is responsible for the security of the site.
 * It defines the authorization of the site.
 */
@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    /**
     * The function defines an admin user with a password and his role as an admin.
     * @param auth Authentication Manager Builder.
     * @throws Exception Thrown when an error occurs in the definition.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("admin")).roles("ADMIN");
    }

    /**
     * The function defines the authorization of the site pages.
     * @param http Http Security.
     * @throws Exception Thrown when an error occurs in the definition.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403.html");
    }
}
