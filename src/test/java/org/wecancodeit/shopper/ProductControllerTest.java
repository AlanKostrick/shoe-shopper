package org.wecancodeit.shopper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.ProductController;
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.models.ProductNotFoundException;
import org.wecancodeit.shopper.models.UserNotFoundException;
import org.wecancodeit.shopper.repositories.ProductRepository;

public class ProductControllerTest {

	@InjectMocks
	private ProductController underTest;

	@Mock
	private Product productOne;
	private Long productId = 1L;

	@Mock
	private Product productTwo;

	@Mock
	private ProductRepository productRepo;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleProductToModel() throws ProductNotFoundException, UserNotFoundException {
		when(productRepo.findById(productId)).thenReturn(Optional.of(productOne));
		underTest.findOneItem(productId, model);
		verify(model).addAttribute("productModel", productOne);
	}

	@Test
	public void shouldAddAllProductsToModel() {
		List<Product> allItems = Arrays.asList(productOne, productTwo);
		when(productRepo.findAll()).thenReturn(allItems);
		underTest.findAllItems(model);
		verify(model).addAttribute("productsModel", allItems);
	}

	@Test
	public void shouldBeAbleToAddAnotheProduct() {
		Product newProduct = new Product("new product", "", "");
		String productName = newProduct.getProductName();
		String productDescription = newProduct.getProductDescription();

		underTest.addItem(productName, productDescription);

		ArgumentCaptor<Product> productArgument = ArgumentCaptor.forClass(Product.class);
		verify(productRepo).save(productArgument.capture());
		assertEquals("new product", productArgument.getValue().getProductName());
	}

}
