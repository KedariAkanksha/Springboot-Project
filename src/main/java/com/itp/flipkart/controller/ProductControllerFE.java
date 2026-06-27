package com.itp.flipkart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itp.flipkart.dto.ProductDto;
import com.itp.flipkart.model.Product;
import com.itp.flipkart.service.ProductService;

@Controller

public class ProductControllerFE {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/home")
	public String homepage() {
		return "landingpage";
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@RequestMapping("/getAllProductsFE")
	public String getAllProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products",products);
		return "show-products";
	}
	
	
	@RequestMapping("/addProductForm")
	String addProductsForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "add-product-form";
	}

	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PostMapping("/addProductFE")
	public String addProductByRequestBody(@ModelAttribute ProductDto productDto,@RequestParam("imageFile") MultipartFile file) throws IOException
	{
		if (!file.isEmpty()) {

	        String uploadDir = "uploads/";
	        Files.createDirectories(Paths.get(uploadDir));

	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        Path filePath = Paths.get(uploadDir, fileName);

	        Files.write(filePath, file.getBytes());

	        // Save only filename or relative path
	        productDto.setImage("http://localhost:8084/uploads/" + fileName);
	    }
 {
		productService.addProduct(productDto);
		return "redirect:/getAllProductsFE";

	}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/deleteProductFE/{id}") 
	public String deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		return "redirect:/getAllProductsFE";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/updateProductForm/{id}") 
	public String updateProductForm(@PathVariable int id, Model model) {
	    Product product = productService.getProductById(id); 
	    model.addAttribute("product", product);
	    return "update-product-form";
	}

	@PostMapping("/updateProductFE/{id}")
	public String updateProductFE(@PathVariable int id,@ModelAttribute("product") ProductDto productDto) {
		productService.updateProduct(id, productDto);
	    return "redirect:/getAllProductsFE";
	}
	
	@RequestMapping(value = "/403")
	public ModelAndView accesssDenied(Principal user)  //currently logged in user is called Principal
	{

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() 
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", 
			    "you do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}
}
