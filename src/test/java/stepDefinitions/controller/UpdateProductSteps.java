package stepDefinitions.controller;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.products.entity.ProductEntity;
import com.products.exception.InvalidPriceException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateProductSteps {
	
	 private ResponseEntity<ProductEntity> response;
	    private RestTemplate restTemplate = new RestTemplate();

	    @Given("a product with id {string} exists")
	    public void a_product_with_id_exists(String id) {
	        // Create or update a product directly (setup for test)
	        String url = "http://localhost:8081/product/" + id;

	        ProductEntity entity = new ProductEntity();
	        entity.setName("Test Phone");
	        entity.setDescription("64gb, Black");
	        entity.setPrice(29999.0);

	        restTemplate.put(url, entity);
	    }

	    @When("I send a PUT request to {string} with:")
	    public void i_send_a_put_request_to_with(String url, List<String> details) {
	        ProductEntity entity = new ProductEntity();
	        entity.setName(details.get(0));
	        entity.setDescription(details.get(1));

	        try {
	            entity.setPrice(Double.parseDouble(details.get(2)));
	        } catch (NumberFormatException e) {
	            throw new InvalidPriceException("handle invalid price gracefully");
	        }

	        try {
	            response = restTemplate.exchange(
	                    "http://localhost:8081" + url,
	                    HttpMethod.PUT,
	                    new HttpEntity<>(entity),   // directly pass entity
	                    ProductEntity.class);
	        } catch (HttpClientErrorException ex) {
	            // Capture error responses (400/404)
	            response = new ResponseEntity<>(null, ex.getStatusCode());
	        }
	    }

	    @Then("the response status should be {int}")
	    public void the_response_status_should_be(Integer expectedStatus) {
	        assertEquals(response.getStatusCodeValue(), expectedStatus.intValue());
	    }
}
