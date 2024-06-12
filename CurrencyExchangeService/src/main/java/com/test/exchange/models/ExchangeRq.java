package com.test.exchange.models;
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.groups.Default;

public class ExchangeRq {

	//@NotEmpty(groups = {Create.class})	
	private String sourceCurrency;
	
	///@NotEmpty(groups = {Create.class})
    private Double sourceAmount;
	
	//@NotEmpty(groups = {Create.class})
    private String targetCurrency;
	
	//@NotEmpty(groups = {Create.class})
    private Double rate;
	
	
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public Double getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
    
//	public interface Create extends Default {}
//	public interface Update extends Default {}
//	public interface List extends Default {}
    
}
