package org.wecancodeit.shopper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.CartItemController;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;

public class CartItemControllerTest {

	@InjectMocks
	private CartItemController underTest;
	
	@Mock
	private CartItem cartItemOne;
	
	@Mock
	private CartItem cartItemTwo;
	
	
	@Mock
	private CartItemRepository cartItemRepo;
	
	@Mock
	private ItemRepository itemRepo;
	
	@Mock
	private Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddCartItemsToModel() {
		Collection<CartItem> cartItems = Arrays.asList(cartItemOne, cartItemTwo);
		when(cartItemRepo.findAll()).thenReturn(cartItems);
		underTest.findAllCartItems(model);
		verify(model).addAttribute("cartItemsModel", cartItems);
	}
	

}
