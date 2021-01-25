package com.yrgo.bff.project.security;

import com.yrgo.bff.project.service.useraccount.UserAccountServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuring Spring Security filter chain
 *
 * @author Bruno Krebs
 * Code source and credit:
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserAccountServiceImplementation userAccountService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Injecting password hashing instance and userAccountService interface
     *
     * @param bCryptPasswordEncoder
     * @param userAccountService
     */
    public WebSecurity(UserAccountServiceImplementation userAccountService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountService = userAccountService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Configures the CORS-filter and define public and private resources
     *
     * @param http - HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() //TODO: Remove this line before prod
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.PUT, "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //TODO: Remove for production
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Defines a custom implementation of UserDetailsService
     *
     * @param auth - AuthenticationManagerBuilder
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAccountService).passwordEncoder(bCryptPasswordEncoder);
    }
//    https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/

    /**
     * Configures our CORS-support, that defines which sources are allowed and which methods
     *
     * @return source - a CorsConfigurationSource, our CORS-configuration
     */
    @Bean   //TODO: Review before prod
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:9090"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Exposed AuthenticationManager as a Bean
     *
     * @return authenticationManagerBean
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
