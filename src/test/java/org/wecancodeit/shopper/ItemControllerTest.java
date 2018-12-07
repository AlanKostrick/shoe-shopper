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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.wecancodeit.shopper.controllers.ItemController;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.models.ItemNotFoundException;
import org.wecancodeit.shopper.repositories.ItemRepository;

public class ItemControllerTest {

	@InjectMocks
	private ItemController underTest;

	@Mock
	private Item itemOne;
	private Long itemOneId = 1L;

	@Mock
	private Item itemTwo;

	@Mock
	private ItemRepository itemRepo;

	@Mock
	private Model model;

	@Mock
	private MockMultipartFile imageFile;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleItemToModel() throws ItemNotFoundException {
		when(itemRepo.findById(itemOneId)).thenReturn(Optional.of(itemOne));
		underTest.findOneItem(itemOneId, model);
		verify(model).addAttribute("itemModel", itemOne);
	}

	@Test
	public void shouldAddAllItemsToModel() {
		List<Item> allItems = Arrays.asList(itemOne, itemTwo);
		when(itemRepo.findAll()).thenReturn(allItems);
		underTest.findAllItems(model);
		verify(model).addAttribute("itemsModel", allItems);
	}

	@Test
	public void shouldAddAdditionalItemsToModel() throws Exception {
		underTest.addItemWithImage("new item name", "", imageFile);

		ArgumentCaptor<Item> itemArgument = ArgumentCaptor.forClass(Item.class);
		verify(itemRepo).save(itemArgument.capture());
		assertEquals("new item name", itemArgument.getValue().getItemName());
	}

}
