package com.itp.flipkart.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class DepartmentDTO {
   
   @NotBlank(message = "Department name is required")
   private String departmentName;
   
   @NotBlank(message = "Department code is required")
   @Pattern(regexp = "^[A-Z]{2,5}-[0-9]{2,5}$", message = "Department code must follow format like IT-001 or HR-001")
   private String departmentCode;
   
   @Size(max = 250, message = "Description cannot exceed 250 characters")
   private String description;
   
   @NotBlank(message = "Department type is required")
   @Pattern(regexp = "^(Technical|Non-Technical)$", message = "Department type must be Technical or Non-Technical")
   private String departmentType;
   
   @NotNull(message = "Budget is required")
   @Positive(message = "Budget must be a positive value")
   @DecimalMin(value = "1000.00", message = "Budget must be at least 1000")
   private Double budget;
   
   @NotBlank(message = "Status is required")
   @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
   private String status;

   @Valid
   private List<EmployeeDTO> employees = new ArrayList<>();

}
