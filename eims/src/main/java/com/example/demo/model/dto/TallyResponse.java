package com.example.demo.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TallyResponse {
	
	@JsonProperty("Region")
	private List<?> region;
}
