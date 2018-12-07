package org.wecancodeit.shopper;

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
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.repositories.CartItemRepository;

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
	
	@Test
	public void shouldAddAdditionalCartItemsToModel() throws Exception {
		Item itemToAdd = new Item("new item name", "", "");
		CartItem cartItem = new CartItem(itemToAdd);
		Long id = cartItem.getId();
		Optional<CartItem> added = cartItemRepo.findById(id);
		added.get();
		System.out.println(added);
		underTest.addItemsToCartFromItemPage(id);

		ArgumentCaptor<CartItem> itemArgument = ArgumentCaptor.forClass(CartItem.class);
		verify(cartItemRepo).save(itemArgument.capture());
		//assertEquals("new item name", itemArgument.getValue().getItem().getItemName());
	}

}
