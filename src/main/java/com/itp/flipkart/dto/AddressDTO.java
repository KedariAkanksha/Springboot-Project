package com.itp.flipkart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder

public class AddressDTO {
	    @NotBlank(message = "Locality is required")
	    @Size(min = 2, max = 100, message = "Locality must be between 2 and 100 characters")
	    private String locality;
	    
	    @NotBlank(message = "City is required")
	    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
	    private String city;
	    
	    @NotBlank(message = "State is required")
	    @Size(min = 2, max = 50, message = "State must be between 2 and 100 characters")
	    private String state;
	    
	    @NotBlank(message = "Country is required")
	    @Size(min = 2, max = 50, message = "Country must be between 2 and 100 characters")
	    private String country;
	    
	    @NotBlank(message = "Zipcode is required")
	    @Pattern(regexp = "^[0-9]{6}$", message = "Zipcode must be 6 digits")
	    private String zipCode;
	}


