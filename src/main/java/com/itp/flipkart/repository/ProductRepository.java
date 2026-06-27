package com.itp.flipkart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
    List<Product> findByPriceGreaterThan(double basePrice);
    
    List<Product> findByPriceBetween(double start, double end);
    
    List<Product> findByCategory(String category);

}
