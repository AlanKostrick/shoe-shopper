package org.wecancodeit.shopper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.ItemController;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.repositories.ItemRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerMockMvcTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemRepository itemRepo;
	
	@Mock
	private Item itemOne;
	Long itemOneId = 1L;
	
	@Mock
	private Item itemTwo;
	
	@Mock
	private Model model;
	
	
	@Test
	public void shouldReturnStatusOfOkForSingleItemPage() throws Exception {
		when(itemRepo.findById(itemOneId)).thenReturn(Optional.of(itemOne));
		this.mockMvc.perform(get("/item?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldRouteToSinglePageView() throws Exception {
		when(itemRepo.findById(itemOneId)).thenReturn(Optional.of(itemOne));
		this.mockMvc.perform(get("/item?id=1")).andExpect(view().name(is("item")));
	}
	
	@Test
	public void shouldReturnStatusOfOkForAllItemsPage() throws Exception {
		when(itemRepo.findAll()).thenReturn(Arrays.asList(itemOne, itemTwo));
		this.mockMvc.perform(get("/items")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldRouteToAllPageView() throws Exception {
		when(itemRepo.findAll()).thenReturn(Arrays.asList(itemOne, itemTwo));
		this.mockMvc.perform(get("/items")).andExpect(view().name(is("items")));
	}
	
	@Test
	public void shouldRedirectUserUponSubmission() throws Exception {
		this.mockMvc.perform(post("/add-item")).andExpect(view().name(is("items")));
	}
	
	
	

}
