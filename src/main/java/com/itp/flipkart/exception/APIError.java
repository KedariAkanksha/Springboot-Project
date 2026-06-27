package com.itp.flipkart.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class APIError {
	String fieldName;				
	Object rejectedValue;      		
	String errorMessage;
	public APIError(String fieldName, Object rejectedValue, String errorMessage) {
		super();
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
		this.errorMessage = errorMessage;
	}
}