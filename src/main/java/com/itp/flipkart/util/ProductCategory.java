package com.itp.flipkart.util;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductCategory {
		
		MENSCLOTHING("men's clothing"),
		JEWELERY("jewelery"),
		ELECTRONICS("electronics"),
		WOMENCLOTHING("women's clothing");
		
		private String category;

		private ProductCategory(String category) {
			this.category = category;
		}

		
				                              //so that we can pass the value not the object
	                                         // i.e in payload pass category : “jewelery” & not //category:”JEWELERY”
	    @JsonValue     		 
	    public String getCategory() {
	        return category;
	    }

	    @JsonCreator
	    public static ProductCategory fromValue(String value) {

	        for (ProductCategory category : ProductCategory.values()) {
	            if (category.category.equalsIgnoreCase(value)) {
	                return category;
	            }
	        }

	        throw new IllegalArgumentException("Invalid category: " + value);
	    }
		

	}



