package com.itp.flipkart.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class StudentTaskDto {

	@NotBlank
	@Size(min = 5, max = 100, message = "Student name must be between 5 to 100 characters")
	private String studentName;
	
	@NotBlank
	@Size(min = 5, max = 100, message = "Student name must be between 5 to 100 characters")
    private String collegeName;
	
    @DecimalMin(value = "40.0", message = "Percentage should be leass than or equal to 40")
    @DecimalMax(value = "100.0",message = "Percentage should not be more than 100")
    private double percentage;
}
