package com.itp.flipkart.service;

import java.util.List;
import java.util.Optional;

//import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.itp.flipkart.dto.ProductDto;
import com.itp.flipkart.exception.ResourceNotFoundException;
import com.itp.flipkart.model.Product;
import com.itp.flipkart.model.Rating;
import com.itp.flipkart.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ProductRepository productRepository;
	
	  //private static final Logger logger=Logger.getLogger(ProductService.class);

		//private static final Logger logger =LoggerFactory.getLogger(ProductService.class);
	
	//Add Multiple Products
	public List<ProductDto> addProducts(List<ProductDto> productDtos) {
		//logger.info("Request received in service to add product " + productDtos.size());
		List<Product> products = productDtos.stream()
				.map(dto -> modelMapper.map(dto, Product.class)).toList();
		
		List<Product> productSavedToDb = productRepository.saveAll(products);
		
		List<ProductDto> convertedProductToDtos = productSavedToDb.stream()
				.map(prod -> modelMapper.map(prod, ProductDto.class)).toList();
		return convertedProductToDtos;
	}

	//Add single product
	@CacheEvict(value = "products", allEntries = true)
	public ProductDto addProduct(ProductDto productdtos) {
		//logger.info("Request received in service to add product " + productdtos.getTitle());
		Product product =modelMapper.map(productdtos, Product.class);
		Product savedProduct = productRepository.save(product);
		//logger.info("Request completed in service to add product " + productdtos.getTitle());
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	//Update the product
	@CacheEvict(value = "products", allEntries = true)
	public ProductDto updateProduct(int id, ProductDto productdtos) {
		
		if(productRepository.existsById(id)) {
			Optional<Product> optional = productRepository.findById(id);
			Product productFromDB = optional.get();
			productFromDB.setTitle(productdtos.getTitle());
			productFromDB.setPrice(productdtos.getPrice());
			productFromDB.setDescription(productdtos.getDescription());
			productFromDB.setCategory(productdtos.getCategory());
			productFromDB.setImage(productdtos.getImage());
			//productFromDB.setRating(productdtos.getRating());
			
			if (productdtos.getRating() != null && productFromDB.getRating() != null) {
                Rating existingRating = productFromDB.getRating();
                existingRating.setRate(productdtos.getRating().getRate());
				existingRating.setCount(productdtos.getRating().getCount());
			}
			
			Product updatedProduct = productRepository.save(productFromDB);
	        
	        return modelMapper.map(updatedProduct, ProductDto.class);
		}
		throw new ResourceNotFoundException("Product with this Id is not exited: " + id);
	}

	//Delete the product
	@CacheEvict(cacheNames = "products", allEntries = true)
	public void deleteProduct(int id) {
		if(productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return; 
		}
		throw new ResourceNotFoundException("Product with ID " + id + " does not exist");
		
	}

	//Get product by id
	public Product getProductById(int id) throws ResourceNotFoundException {
		if (productRepository.existsById(id)) {
			Optional<Product> optProduct = productRepository.findById(id);
			return optProduct.get();
		}
		throw new ResourceNotFoundException("Product with ID " + id + " does not exist");
	}

	//get all products
	@Cacheable("products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	//get product above base price
	public List<Product> getProductAboveBasePrice(double basePrice) {
	    return productRepository.findByPriceGreaterThan(basePrice);
	}

	//get product between start and end
	public List<Product> getProductBetweenPriceRange(double start, double end) {
	    return productRepository.findByPriceBetween(start, end);
	}

	//get product bt category
	public List<Product> getProductByCategory(String category) {
	    return productRepository.findByCategory(category);
	}

}
