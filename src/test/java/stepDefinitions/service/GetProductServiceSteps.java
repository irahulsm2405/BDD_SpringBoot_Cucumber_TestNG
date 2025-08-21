package stepDefinitions.service;

import static org.testng.Assert.*;

import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.products.entity.ProductEntity;
import com.products.exception.ProductDoesNotExistException;
import com.products.repository.ProductRepository;
import com.products.service.ProductService;
import com.products.service.ProductServiceImpl;

import io.cucumber.java.en.*;

import static org.mockito.Mockito.*;

public class GetProductServiceSteps {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<ProductEntity> productList;
    private ProductEntity resultProduct;
    private Exception thrownException;

    public GetProductServiceSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("Product service is available")
    public void product_service_is_available() {
    }

    @When("Requesting all products")
    public void requesting_all_products() {
        productList = Arrays.asList(
                new ProductEntity(1, "Iphone 15 Pro", "256GB, Deep Purple", 149990.00),
                new ProductEntity(2, "Samsung S24", "128GB, Black", 99990.00)
        );

        when(productRepository.findAll()).thenReturn(productList);

        productList = productService.getAllProducts();
    }

    @Then("A list of products should be returned")
    public void a_list_of_products_should_be_returned() {
        assertNotNull(productList);
        assertEquals(productList.size(), 2);
        assertEquals(productList.get(0).getName(), "Iphone 15 Pro");
    }

    @When("Requesting product with ID {int}")
    public void requesting_product_with_id(Integer id) {
        try {
            if (id == 1) {
                ProductEntity product = new ProductEntity(1, "Iphone 15 Pro", "256GB, Deep Purple", 149990.00);
                when(productRepository.findById(1)).thenReturn(Optional.of(product));
            } else {
                when(productRepository.findById(any())).thenReturn(Optional.empty());
            }

            resultProduct = productService.getProductById(id);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("Product with ID {int} should be returned")
    public void product_with_id_should_be_returned(Integer id) {
        assertNotNull(resultProduct);
        assertEquals(resultProduct.getId(), id.longValue());
    }

    @Then("ProductDoesNotExistException should be thrown")
    public void product_not_found_exception_should_be_thrown() {
        assertTrue(thrownException instanceof ProductDoesNotExistException);
    }
}
