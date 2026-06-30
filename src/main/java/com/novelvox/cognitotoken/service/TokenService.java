package com.novelvox.cognitotoken.service;

import com.novelvox.cognitotoken.constants.Constants;
import com.novelvox.cognitotoken.dto.TokenRequest;
import com.novelvox.cognitotoken.dto.TokenResponse;
import com.novelvox.cognitotoken.model.CognitoSecret;
import com.novelvox.cognitotoken.util.TimestampValidator;

public class TokenService {

	private final SecretManagerService secretManagerService;
	private final CognitoOAuthService cognitoOAuthService;

	public TokenService() {
		this.secretManagerService = new SecretManagerService();
		this.cognitoOAuthService = new CognitoOAuthService();
	}

	public TokenResponse generateToken(TokenRequest request) {

		validateRequest(request);

		CognitoSecret cognitoSecret = secretManagerService.getSecret();

		return cognitoOAuthService.generateToken(cognitoSecret);
	}

	private void validateRequest(TokenRequest request) {

		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}

		if (!TimestampValidator.isValid(request.getTimestamp())) {
			throw new IllegalArgumentException("Timestamp is invalid or expired.");
		}

		if (request.getUserId() == null || request.getUserId().isBlank()) {
			throw new IllegalArgumentException("UserId is mandatory.");
		}else if(request.getUserId().equals(Constants.USER_KEY)) {
			return;
		}else {
			throw new IllegalArgumentException("UserId is mandatory.");
		}
	}
}