package com.example.demo.model.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CurrencyResponse {
	
	private String currency;
	
	private long tally;

}