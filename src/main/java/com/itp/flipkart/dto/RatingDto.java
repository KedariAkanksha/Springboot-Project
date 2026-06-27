package com.itp.flipkart.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RatingDto {

	@DecimalMin(value = "0", message = "Rating rate cannot be less than 0.0")
	@DecimalMax(value = "100000.00" , message = "Rating rate cannot be greater than 100000.00")
	public double rate;
	
	@DecimalMin(value = "0", message = "Rating count cannot be less than 0")
	public int count;
}
