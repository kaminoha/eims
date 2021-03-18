package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RatioResponse {
	
	@JsonProperty("Male")
	private long male;
	
	@JsonProperty("Female")
	private long female;

	public long getMale() {
		return male;
	}

	public void setMale(long male) {
		this.male = male;
	}

	public long getFemale() {
		return female;
	}

	public void setFemale(long female) {
		this.female = female;
	}

}
