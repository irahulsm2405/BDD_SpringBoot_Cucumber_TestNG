package stepDefinitions.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.products.entity.ProductEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteProductSteps {
	
	private String baseUrl;
	ResponseEntity<ProductEntity> response;
	private int deletedProductId;
	RestTemplate restTemplate;
	
	@Given("Sending request to delete products end point")
	public void sending_request_to_delete_products_end_point() {
		baseUrl = "http://localhost:8081/product";
	}
	
	@When("A delete request is sent to deleteProducts with id \\{{int}}")
	public void a_delete_request_is_sent_to_delete_products_with_id(Integer productId) {
	    restTemplate = new RestTemplate();
	    restTemplate.delete(baseUrl + "/" + productId);
	    
	    // save deleted productId for later assertion
	    this.deletedProductId = productId;
	}

	@Then("Delete products")
	public void delete_products() {
	    try {
	        ResponseEntity<ProductEntity> response = restTemplate.getForEntity(baseUrl + "/" + deletedProductId, ProductEntity.class);

	        // If product still exists → fail
	        fail("Product was not deleted, still found: " + response.getBody());

	    } catch (HttpClientErrorException e) {
	        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
	    }
	}

	@When("A deleteAll request is sent")
	public void a_delete_all_request_is_sent() {
		RestTemplate restTemplate = new RestTemplate();
	    restTemplate.delete(baseUrl);
	}

	@Then("Delete all products")
	public void delete_all_products() {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<ProductEntity[]> response =
	            restTemplate.getForEntity(baseUrl, ProductEntity[].class);

	    // Assert the list is empty
	    assertEquals(0, response.getBody().length, "Products were not deleted successfully!");
	}
}
