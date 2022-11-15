package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

	@JsonProperty("isSuccess")
	private boolean status;
	
	@JsonProperty("Message")
	private String message;
	
	@JsonProperty("authToken")
	private String token;
	
}
