package com.itp.flipkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itp.flipkart.dto.ProductDto;
import com.itp.flipkart.exception.ResourceNotFoundException;
import com.itp.flipkart.model.Product;
import com.itp.flipkart.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Product Controller", description = "Endpoints for creating, fetching, updating, and deleting retail products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Value("${info.project.instructor.name}")
	public String instructorName;
	
	@GetMapping("/greet")
	public String greet() {
		return instructorName;
	}
	
	//private static final Logger logger=Logger.getLogger(ProductController.class);
	
	//private static final Logger logger =LoggerFactory.getLogger(ProductController.class);
	
	
	@Operation(summary = "Saves single Product", description = "Accepts a single product payload, runs validation checks on category, pricing, and title limits, and returns the persisted product record with a unique database ID.")
	//description can be long as you need to, this description is called as documentation for client 
	@PostMapping("/addProduct")
	public ResponseEntity<ProductDto> addSingleProduct(@Valid @RequestBody ProductDto productdtos){
		//logger.info("Request received in controller to add product " + productdtos.getTitle());
		return new ResponseEntity <ProductDto>(productService.addProduct(productdtos),HttpStatus.CREATED);
	} 

	@Operation(summary = "Saves multiples Product", description = "Accepts an array list of product payloads. Processes and persists all records together in a single bulk transaction.")
	@PostMapping("/addProducts")
	public ResponseEntity <List<ProductDto>> addMultipleProducts(@Valid @RequestBody List<ProductDto> productdtos){
		//logger.info("Request received in controller to add product " + productdtos.size());
		return new ResponseEntity <List<ProductDto>>(productService.addProducts(productdtos),HttpStatus.CREATED);
	} 
	
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable int id,@Valid @RequestBody ProductDto productdtos){
		return new ResponseEntity <ProductDto>(productService.updateProduct(id,productdtos),HttpStatus.CREATED);
	} 
	
	//by default swagger only knows 200 OK response, so we add potential response to it
	@Operation(summary = "Delete a product by its ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Product was successfully deleted from the system."),
	    @ApiResponse(responseCode = "404", description = "Resource Clean Fail: The provided Product ID does not exist in the database.")
	})
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		productService.deleteProduct(id);
		return new ResponseEntity<String> ("Product id  " + id + " is deleted successfully. " , HttpStatus.OK);
	}
	
	@Operation(summary = "Get a Product by ID", description = "Fetches a single product details from the database using its unique identifier.")
	@GetMapping("/getProduct/{id}")
	public ResponseEntity<?> getProductById(@Parameter(description = "The primary key ID of the product you want to fetch", example = "1")
	                                         @PathVariable int id) {
		try {
			Product product = productService.getProductById(id);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (ResourceNotFoundException ex1) {
             return new ResponseEntity<String>(ex1.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	
	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/getProductAboveBasePrice/{basePrice}")
	public ResponseEntity<List<Product>> getProductAboveBasePrice(@PathVariable double basePrice) {
	    List<Product> products = productService.getProductAboveBasePrice(basePrice);
	    return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/getProductBetweenPriceRange/{start}/{end}")
	public ResponseEntity<List<Product>> getProductBetweenPriceRange(@PathVariable double start, @PathVariable double end) {
	    List<Product> products = productService.getProductBetweenPriceRange(start, end);
	    return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/getProductByCategory/{category}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
	    List<Product> products = productService.getProductByCategory(category);
	    return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
}
