package com.itp.flipkart.dto;

import com.itp.flipkart.util.ProductCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProductDto {
	
	@Schema(description = "The unique commercial title of the product", example = "Mens Cotton Jacket")
	@NotBlank(message = "Product title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
	public String title;
	
	@Schema(description = "Retail price per item in Ruppes", example = "100")
	@Positive(message = "Price must be a positive number greater than 0")
    public double price;
	
	@Schema(description = "Product classification description", example = "great outerwear jackets for Mens, suitable for many occasions, deep adjustable hood with royalcord, side pockets.")
	@NotBlank(message = "Description is required")
    public String description;
	
	@Schema(description = "Product category", example = "MENSCLOTHING")
	//@NotBlank works on string
	@NotNull(message = "Category is required")
	public ProductCategory category;

	@Schema(description = "Product image", example = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg")
	@NotBlank(message = "Image is required")
    public String image;
	
	@Schema(description = "Product rating", example = "4.1")
	@NotNull(message = "Rating details are required")
    @Valid
    public RatingDto rating;

}
