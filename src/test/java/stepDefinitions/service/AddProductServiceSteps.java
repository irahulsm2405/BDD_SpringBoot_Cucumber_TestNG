package stepDefinitions.service;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.util.List;

import com.products.entity.ProductEntity;
import com.products.exception.InvalidDescriptionException;
import com.products.exception.InvalidNameException;
import com.products.exception.InvalidPriceException;
import com.products.repository.ProductRepository;
import com.products.service.ProductServiceImpl;

import io.cucumber.java.en.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddProductServiceSteps {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity product;
    private ProductEntity savedProduct;
    private Exception exception;

    public AddProductServiceSteps() {
        // Initialize Mockito mocks for this class
        MockitoAnnotations.openMocks(this);
    }

    @Given("Add new product using Service class")
    public void add_new_product_using_service_class() {
        // nothing to do, setup handled in scenarios
    }

    @When("All product details provided are valid")
    public void all_product_details_provided_are_valid(List<String> details) {
        product = new ProductEntity();
        product.setName(details.get(0));
        product.setDescription(details.get(1));
        product.setPrice(Double.parseDouble(details.get(2)));

        when(productRepository.save(product)).thenReturn(product);

        savedProduct = productService.addNewProduct(product);
    }

    @Then("return the product saved")
    public void return_the_product_saved() {
        assertNotNull(savedProduct);
        assertEquals(savedProduct.getName(), "Iphone 15 pro");
        verify(productRepository, times(1)).save(product);
    }

    @When("Provided null to any parameter")
    public void provided_null_to_any_parameter(List<String> details) {
        product = new ProductEntity();
        product.setName(details.get(0));
        product.setDescription(details.get(1));
        
        if ("null".equalsIgnoreCase(details.get(2))) {
            product.setPrice(0.00); // Assuming ProductEntity.price is Double, not primitive double
        } else {
        	product.setPrice(Double.parseDouble(details.get(2)));
        }

        when(productRepository.save(product)).thenReturn(product);

        try {
            product = productService.addNewProduct(product);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("Throw invalid name exception")
    public void throw_invalid_name_exception() {
        assertTrue(exception instanceof InvalidNameException, 
                   "Expected InvalidNameException but got " + exception);
    }

    @Then("Throw invalid description exception")
    public void throw_invalid_description_exception() {
        assertTrue(exception instanceof InvalidDescriptionException, 
                   "Expected InvalidDescriptionException but got " + exception);
    }

    @Then("Throw invalid price exception")
    public void throw_invalid_price_exception() {
        assertTrue(exception instanceof InvalidPriceException, 
                   "Expected InvalidPriceException but got " + exception);
    }
}
