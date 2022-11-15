package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RatioResponse {
	
	@JsonProperty("Male")
	private long male;
	
	@JsonProperty("Female")
	private long female;
}
