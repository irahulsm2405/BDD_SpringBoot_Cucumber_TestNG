package stepDefinitions.service;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.products.entity.ProductEntity;
import com.products.exception.ProductDoesNotExistException;
import com.products.repository.ProductRepository;
import com.products.service.ProductServiceImpl;

import io.cucumber.java.en.*;

public class DeleteProductServiceSteps {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Exception thrownException;

    public DeleteProductServiceSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("Product delete method in service class is available")
    public void product_service_is_available() {
        // Context setup
    }

    @When("Requesting to delete product with ID {int}")
    public void requesting_to_delete_product_with_id(Integer id) {
        try {
            if (id == 1) {
                //product exists
                ProductEntity product = new ProductEntity(1, "Iphone 15 Pro", "256GB, Deep Purple", 149990.00);
                when(productRepository.findById(1)).thenReturn(Optional.of(product));
            } else {
                //product not found
                when(productRepository.findById(id)).thenReturn(Optional.empty());
                throw new ProductDoesNotExistException("Product does not exists");
            }

            productService.deleteProductById(id);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("Product with ID {int} should be deleted successfully")
    public void product_with_id_should_be_deleted_successfully(Integer id) {
        verify(productRepository, times(1)).deleteById(id);
        assertNull(thrownException);
    }

    @Then("Throw new ProductDoesNotExistException")
    public void product_not_found_exception_should_be_thrown() {
        assertTrue(thrownException instanceof ProductDoesNotExistException);
    }
}
