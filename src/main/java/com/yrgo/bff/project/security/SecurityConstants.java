package com.yrgo.bff.project.security;

/**
 *
 *
 * @author Bruno Krebs
 * Code source and credit:
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 *
 * Security constants for Spring Security
 */
public class SecurityConstants {

    public static final String SECRET = "SECRETKEY";
    public static final long EXPIRATION_TIME = 900_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user";
}