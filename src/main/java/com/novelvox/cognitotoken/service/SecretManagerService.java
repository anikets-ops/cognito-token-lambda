package com.novelvox.cognitotoken.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelvox.cognitotoken.constants.Constants;
import com.novelvox.cognitotoken.model.CognitoSecret;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretManagerService {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public CognitoSecret getSecret() {

		try (SecretsManagerClient client = SecretsManagerClient.builder().region(Region.US_EAST_1).build()) {

			GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(Constants.SECRET_NAME).build();

			GetSecretValueResponse response = client.getSecretValue(request);

			return objectMapper.readValue(response.secretString(), CognitoSecret.class);

		} catch (Exception ex) {
			throw new RuntimeException("Unable to read Cognito Secret", ex);
		}
	}
}