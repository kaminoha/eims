package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TallyResponse {
	
	@JsonProperty("Region")
	private List<?> region;

	public List<?> getRegion() {
		return region;
	}

	public void setRegion(List<?> list) {
		this.region = list;
	}
}
