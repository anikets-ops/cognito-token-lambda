package com.novelvox.cognitotoken;

import com.novelvox.cognitotoken.dto.TokenRequest;
import com.novelvox.cognitotoken.dto.TokenResponse;
import com.novelvox.cognitotoken.service.TokenService;

public class CognitotokenApplication {

	public static void main(String[] args) {

		TokenService tokenService = new TokenService();

		TokenRequest request = new TokenRequest();
		request.setUserId("Adeeba");
		request.setTimestamp(System.currentTimeMillis());

		try {

			TokenResponse response = tokenService.generateToken(request);

			System.out.println("============== TOKEN RESPONSE ==============");
			System.out.println("Access Token : " + response.getAccessToken());
			System.out.println("Token Type   : " + response.getTokenType());
			System.out.println("Expires In   : " + response.getExpiresIn());
			System.out.println("Status       : " + response.getStatus());
			System.out.println("Message      : " + response.getMessage());

		} catch (Exception ex) {

			System.err.println("============== ERROR ==============");
			ex.printStackTrace();
		}
	}
}
