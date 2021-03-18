package com.example.demo.model;

public class CurrencyResponse {
	
	private String currency;
	
	private long tally;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public long getTally() {
		return tally;
	}
	public void setTally(long tally) {
		this.tally = tally;
	}
	
	@Override
	public String toString() {
		return "CurrencyResponse [currency=" + currency + ", tally=" + tally + "]";
	}
	
	public CurrencyResponse(String currency, long tally) {
		super();
		this.currency = currency;
		this.tally = tally;
	}
}