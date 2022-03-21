package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()  // Handle authentication errors
                    .authenticationEntryPoint(
                            (request, response, ex) -> response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            )
                    )
                    .and()
                .authorizeRequests()  // Require all requests to be authenticated except for select few
                    .antMatchers(HttpMethod.POST, "/answer", "/user").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()  // Handle login
                    .loginProcessingUrl("/login")
                    .successHandler((request, response, auth) -> response.setStatus(
                            HttpServletResponse.SC_OK
                    ))
                    .failureHandler((request, response, ex) -> response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                    ))
                    .permitAll()
                    .and()
                .logout()  // Handle logout
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler((request, response, auth) -> response.setStatus(
                            HttpServletResponse.SC_OK
                    ))
                    .and()
                .cors().and()
                .csrf().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        List<String> allowed = new ArrayList<>();
        allowed.add("localhost:3000");
        allowed.add("http://localhost:3000");
        config.setAllowedOrigins(allowed);

        List<String> methods = new ArrayList<>();
        methods.add("POST");
        methods.add("GET");
        methods.add("DELETE");
        methods.add("PUT");
        methods.add("OPTIONS");
        config.setAllowedMethods(methods);

        List<String> headers = new ArrayList<>();
        headers.add("Content-Type");
        headers.add("Authorization");
        headers.add("Set-Cookie");
        headers.add("registerCorsConfiguration");

        config.setAllowedHeaders(headers);
        config.setExposedHeaders(headers);
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Exclude h2 console from authentication restrictions
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
