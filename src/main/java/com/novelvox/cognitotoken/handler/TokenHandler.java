package com.novelvox.cognitotoken.handler;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelvox.cognitotoken.dto.TokenRequest;
import com.novelvox.cognitotoken.dto.TokenResponse;
import com.novelvox.cognitotoken.service.TokenService;

public class TokenHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private final ObjectMapper objectMapper;
	private final TokenService tokenService;

	public TokenHandler() {
		this.objectMapper = new ObjectMapper();
		this.tokenService = new TokenService();
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {

		LambdaLogger logger = context.getLogger();

		try {

			logger.log("Generate Token Request Received.");

			TokenRequest tokenRequest = objectMapper.readValue(request.getBody(), TokenRequest.class);

			logger.log("Request received for UserId : " + tokenRequest.getUserId());

			TokenResponse response = tokenService.generateToken(tokenRequest);

			return buildResponse(200, response);

		} catch (IllegalArgumentException ex) {

			logger.log("Validation Error : " + ex.getMessage());

			return buildResponse(400, Map.of("status", "FAILED", "message", ex.getMessage()));

		} catch (Exception ex) {

			logger.log("Unexpected Error : " + ex.getMessage());

			return buildResponse(500, Map.of("status", "FAILED", "message", "Internal Server Error"));
		}
	}

	private APIGatewayProxyResponseEvent buildResponse(int statusCode, Object body) {

		try {

			return new APIGatewayProxyResponseEvent().withStatusCode(statusCode).withHeaders(defaultHeaders())
					.withBody(objectMapper.writeValueAsString(body));

		} catch (Exception ex) {

			throw new RuntimeException(ex);
		}
	}

	private Map<String, String> defaultHeaders() {

	    Map<String, String> headers = new HashMap<>();

	    headers.put("Content-Type", "application/json");
	    headers.put("Access-Control-Allow-Origin", "https://tccu.novelvox.net");
	    headers.put("Access-Control-Allow-Headers",
	            "Content-Type,Authorization,X-Amz-Date,X-Api-Key,X-Amz-Security-Token");
	    headers.put("Access-Control-Allow-Methods", "OPTIONS,POST");

	    return headers;
	}

}