package com.brightcoding.app.ws.security;
//infos necessaires pour utiliser le token
public class SecurityConstants {
	public static final long EXPIRATION_TIME = 864000000; //dix jours c'est la date d'expiration du token 
	public static final String TOKEN_PREFIX = "Bearer " ;
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users" ;
	public static final String TOKEN_SECRET = "dfg893hc475zwerop4tghg4dfeqaas?=-0ljzm0-9";
}
