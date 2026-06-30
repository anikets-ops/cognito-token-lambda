package com.novelvox.cognitotoken.util;

public final class TimestampValidator {

	private static final long ALLOWED_WINDOW = 180000L;

	private TimestampValidator() {
	}

	public static boolean isValid(Long requestTimestamp) {

		if (requestTimestamp == null) {
			return false;
		}

		long currentTime = System.currentTimeMillis();

		return Math.abs(currentTime - requestTimestamp) <= ALLOWED_WINDOW;
	}
}