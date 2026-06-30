package com.novelvox.cognitotoken.constants;

public final class Constants {

	private Constants() {
	}

	public static final String SECRET_NAME = "prod/cognito";

	public static final String GRANT_TYPE = "client_credentials";

	public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";

	public static final String TOKEN_URL = "https://us-east-1mbtd6etwi.auth.us-east-1.amazoncognito.com/oauth2/token";

	public static final String USER_KEY = getEnv("USER_KEY", "");


	private static String getEnv(String key, String defaultValue) {
		String value = System.getenv(key);
		return (value != null && !value.isBlank()) ? value : defaultValue;
	}



}