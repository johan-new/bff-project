package com.yrgo.bff.project.security;

public class SecurityConstants {

    public static final String SECRET = "SECRETKEY";
    public static final long EXPIRATION_TIME = 900_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user";
}