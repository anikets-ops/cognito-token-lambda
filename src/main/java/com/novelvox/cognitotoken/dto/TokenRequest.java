package com.novelvox.cognitotoken.dto;

import lombok.Data;

@Data
public class TokenRequest {

	private String userId;
	private Long timestamp;

}