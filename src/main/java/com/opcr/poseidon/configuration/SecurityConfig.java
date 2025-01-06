package com.opcr.poseidon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuration in 4 Parts.
     * authorizeHttpRequests : block access to the app if not log. Open the /login and / to everyone. To access /user/** the user need to have ADMIN role.
     * exceptionHandling : for handling error 403 when USER can't access /user/**
     * formLogin : for the custom login page and redirection if logged.
     * logout : handling of the logout, end of session and finally the redirection.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests
                        (authorize -> authorize
                                .requestMatchers("/", "login").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .anyRequest().authenticated())

                .exceptionHandling
                        (exceptionHandling -> exceptionHandling
                                .accessDeniedPage("/403"))

                .formLogin
                        (formLogin -> formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/bidList/list", true)
                                .permitAll())

                .logout
                        (logout -> logout
                                .logoutUrl("/app-logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID"));

        return http.build();
    }

    /**
     * Return the BCryptPasswordEncoder for the password hashing.
     *
     * @return the BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Return a custom UserDetailsService.
     *
     * @return the PoseidonUserDetailsService.
     */
    @Bean
    public PoseidonUserDetailsService userDetailsService() {
        return new PoseidonUserDetailsService();
    }

    /**
     * DaoAuthenticationProvider is set with the BCryptPasswordEncode and the PoseidonUserDetailsService.
     *
     * @return the AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
}
