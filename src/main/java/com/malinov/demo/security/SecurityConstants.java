package com.malinov.demo.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 86400000; // 1 day expressed in milliseconds 24*60*60*1000
    public static final String JWT_SECRET_KEY = "504E635266556AZERTY6E6472357538792F413F4428492C7B6250645367566B5970";
    public static final String GET_COMPANY_NAME = "Demo Company";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'accéder à ces ressources.";

}
