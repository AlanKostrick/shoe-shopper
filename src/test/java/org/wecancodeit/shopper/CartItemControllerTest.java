package org.wecancodeit.shopper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.CartItemController;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.CartNotFoundException;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ProductRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

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
	private ProductRepository productRepo;

	@Mock
	private UserRepository userRepo;

	@Mock
	private Model model;

	@Mock
	Principal principal;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddCartItemsToModel() throws CartNotFoundException {
		User user = new User("admin", "admin", "ADMIN");
		when(userRepo.findByUsername("admin")).thenReturn(Optional.of(user));
		Collection<CartItem> cartItems = Arrays.asList(cartItemOne, cartItemTwo);
		when(cartItemRepo.findAll()).thenReturn(cartItems);
		underTest.findAllCartItems(model);
		verify(model).addAttribute("cartItemsModel", cartItems);
	}

}
