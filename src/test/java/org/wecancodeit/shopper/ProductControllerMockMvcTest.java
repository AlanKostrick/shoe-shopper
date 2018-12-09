package org.wecancodeit.shopper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.ImageUploadService;
import org.wecancodeit.shopper.controllers.ProductController;
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.ProductRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerMockMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ImageUploadService imageUploadService;

	@MockBean
	private ProductRepository productRepo;

	@MockBean
	private UserRepository userRepo;

	@Mock
	private Product itemOne;
	Long itemOneId = 1L;

	@Mock
	private Product itemTwo;

	@Mock
	private Model model;


	@Before
	public void setup() {
		User user = new User("admin", "admin", "ADMIN");
		when(userRepo.findByUsername("admin")).thenReturn(Optional.of(user));
	}

	@WithMockUser(username = "admin")
	@Test
	public void shouldReturnStatusOfOkForSingleItemPage() throws Exception {
		when(productRepo.findById(itemOneId)).thenReturn(Optional.of(itemOne));
		this.mockMvc.perform(get("/product?id=1")).andExpect(status().isOk());
	}

	@WithMockUser(username = "admin")
	@Test
	public void shouldRouteToSinglePageView() throws Exception {
		when(productRepo.findById(itemOneId)).thenReturn(Optional.of(itemOne));
		this.mockMvc.perform(get("/product?id=1")).andExpect(view().name(is("product")));
	}

	@WithMockUser(username = "admin")
	@Test
	public void shouldReturnStatusOfOkForAllItemsPage() throws Exception {
		when(productRepo.findAll()).thenReturn(Arrays.asList(itemOne, itemTwo));
		this.mockMvc.perform(get("/products")).andExpect(status().isOk());
	}

	@WithMockUser(username = "admin")
	@Test
	public void shouldRouteToAllPageView() throws Exception {
		when(productRepo.findAll()).thenReturn(Arrays.asList(itemOne, itemTwo));
		this.mockMvc.perform(get("/products")).andExpect(view().name(is("products")));
	}

}
