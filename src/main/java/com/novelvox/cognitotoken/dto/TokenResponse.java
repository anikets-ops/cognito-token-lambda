package com.novelvox.cognitotoken.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

	private String accessToken;
	private String tokenType;
	private Integer expiresIn;

	private String status;
	private String message;
}