package com.novelvox.cognitotoken.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelvox.cognitotoken.constants.Constants;
import com.novelvox.cognitotoken.dto.TokenResponse;
import com.novelvox.cognitotoken.model.CognitoSecret;

public class CognitoOAuthService {

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public CognitoOAuthService() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
	}

	public TokenResponse generateToken(CognitoSecret secret) {

		try {

			String requestBody = buildRequestBody(secret);

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.TOKEN_URL))
					.header("Content-Type", Constants.CONTENT_TYPE)
					.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				throw new RuntimeException("Failed to generate Cognito token. Status Code : " + response.statusCode()
						+ " Response : " + response.body());
			}

			return parseResponse(response.body());

		} catch (IOException | InterruptedException ex) {

			Thread.currentThread().interrupt();
			throw new RuntimeException("Error while calling Cognito OAuth endpoint", ex);
		}
	}

	private String buildRequestBody(CognitoSecret secret) {

		return "grant_type=" + URLEncoder.encode(Constants.GRANT_TYPE, StandardCharsets.UTF_8) + "&client_id="
				+ URLEncoder.encode(secret.getClientId(), StandardCharsets.UTF_8) + "&client_secret="
				+ URLEncoder.encode(secret.getClientSecret(), StandardCharsets.UTF_8);
	}

	private TokenResponse parseResponse(String responseBody) throws IOException {

		JsonNode jsonNode = objectMapper.readTree(responseBody);

		return TokenResponse.builder().accessToken(jsonNode.get("access_token").asText())
				.tokenType(jsonNode.get("token_type").asText()).expiresIn(jsonNode.get("expires_in").asInt())
				.status("SUCCESS").message("Token generated successfully.").build();
	}
}