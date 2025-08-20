package stepDefinitions.controller;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.products.entity.ProductEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetProductSteps {
	
	private String baseUrl;
	ResponseEntity<ProductEntity> response;
	ResponseEntity<ProductEntity[]> responseArray;
	
	@Given("Sending request to get products end point")
	public void sending_request_to_get_products_end_point() {
		baseUrl = "http://localhost:8081/product";
	}

	@When("A get request is sent to getAllProducts")
	public void a_get_request_is_sent_to_get_all_products() {
	    RestTemplate restTemplate = new RestTemplate();
	    responseArray = restTemplate.getForEntity(baseUrl, ProductEntity[].class);
	}

	@Then("Return list of products")
	public void return_list_of_products() {
	    assertEquals(HttpStatus.OK, responseArray.getStatusCode());
	}

	@When("A get request is sent to getAllProducts with id \\{{int}}")
	public void a_get_request_is_sent_to_get_all_products_with_id(Integer productId) {
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	        response = restTemplate.getForEntity(baseUrl + "/" + productId, ProductEntity.class);
	    } catch (HttpClientErrorException.NotFound e) {
	        // Create a ResponseEntity with 404 status
	        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        System.out.println("Product not found: " + productId);
	    }
	}

	@Then("Return product")
	public void return_product() {
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Then("Throw exception that no products available")
	public void throw_exception_that_no_products_available() {
		assertEquals(HttpStatus.NOT_FOUND, responseArray.getStatusCode());
	}
	
	@Then("Throw exception that product does not exist")
	public void throw_exception_that_product_does_not_exist() {
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
