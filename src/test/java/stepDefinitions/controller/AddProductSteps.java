package stepDefinitions.controller;

import static org.testng.Assert.*;

import java.util.List;


import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.products.entity.ProductEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddProductSteps {
	
	private String baseUrl;
	ResponseEntity<ProductEntity> response;
	
	private Exception exception;
	
	@Given("Sending request to add new product end point")
	public void sending_request_to_add_new_product_end_point() {
		 baseUrl = "http://localhost:8081/product";
	}

	// Scenario: Adding a new product all valid detail
	@When("All product details provided are valid and request is sent")
	public void all_product_details_provided_are_valid_and_request_is_sent(List<String> details) {
		
		ProductEntity entity = new ProductEntity();
		entity.setName(details.get(0));
		entity.setDescription(details.get(1));
		entity.setPrice(Double.parseDouble(details.get(2)));
		
        // Preparing REST template request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductEntity> request = new HttpEntity<>(entity, headers);

        // Send POST request will return product entity. Will be used for assertion
        response = restTemplate.postForEntity(baseUrl, request, ProductEntity.class);
		
	}

	@Then("return the saved product")
	public void save_product() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ProductEntity savedProduct = response.getBody();
        assertEquals("Iphone 15 pro", savedProduct.getName());
        assertEquals("256gb, Deep Purple", savedProduct.getDescription());
        assertEquals(149990.00, savedProduct.getPrice());
	}
	
	// Scenario: Adding a new product with null name
	@When("Provided any parameter as null.")
	public void provided_name_as_null(List<String> details) {
		
		ProductEntity entity = new ProductEntity();
		entity.setName(details.get(0));
		entity.setDescription(details.get(1));
		
		String priceStr = details.get(2);
		if (priceStr == null || priceStr.equalsIgnoreCase("null") || priceStr.isEmpty()) {
		    entity.setPrice(0.0); // or leave unset, depending on your validation
		} else {
		    entity.setPrice(Double.parseDouble(priceStr));
		}
		
        // Preparing REST template request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductEntity> request = new HttpEntity<>(entity, headers);

        // Send POST request will return product entity. Will be used for assertion
        try {
        response = restTemplate.postForEntity(baseUrl, request, ProductEntity.class);
        }catch(Exception e) {
        	this.exception=e;
        	System.out.println(e);
        }
		
	}
	
	@Then("throw invalid details exception")
	public void throw_invalid_name_error() {
		assertNotNull(exception);
	    // Check if it’s an HTTP client error
	    if (exception instanceof HttpClientErrorException) {
	        HttpClientErrorException ex = (HttpClientErrorException) exception;
	        assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST, "Expected 400 BAD_REQUEST");
	    } else {
	        fail("Expected HttpClientErrorException, but got: " + exception.getClass().getSimpleName());
	    }
	}
	
}
