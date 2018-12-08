package org.wecancodeit.shopper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.ProductController;
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.models.ProductNotFoundException;
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

	@Mock
	private MockMultipartFile imageFile;
	
	@Mock
	private Principal principal;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleItemToModel() throws ProductNotFoundException {
		when(productRepo.findById(productId)).thenReturn(Optional.of(productOne));
		underTest.findOneItem(productId, principal, model);
		verify(model).addAttribute("itemModel", productOne);
	}

	@Test
	public void shouldAddAllItemsToModel() {
		List<Product> allItems = Arrays.asList(productOne, productTwo);
		when(productRepo.findAll()).thenReturn(allItems);
		underTest.findAllItems(model);
		verify(model).addAttribute("itemsModel", allItems);
	}

	@Test
	public void shouldAddAdditionalItemsToModel() throws Exception {
		underTest.addItemWithImage("new item name", "", imageFile);

		ArgumentCaptor<Product> itemArgument = ArgumentCaptor.forClass(Product.class);
		verify(productRepo).save(itemArgument.capture());
		assertEquals("new item name", itemArgument.getValue().getProductName());
	}

}
