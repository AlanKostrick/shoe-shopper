package org.wecancodeit.shopper;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.shopper.controllers.CartItemController;
import org.wecancodeit.shopper.controllers.ImageUploadService;
import org.wecancodeit.shopper.controllers.ItemController;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

	@Resource
	private ItemRepository itemRepo;

	@Resource
	private ItemController itemController;

	@Resource
	private CartItemRepository cartItemRepo;

	@Resource
	private CartItemController cartItemController;
	
	@Resource
	private ImageUploadService imageUploadService;

	@Test
	public void contextLoads() throws Exception {
		assertThat(itemRepo).isNotNull();
		assertThat(itemController).isNotNull();
		assertThat(cartItemRepo).isNotNull();
		assertThat(cartItemController).isNotNull();
		assertThat(imageUploadService).isNotNull();

	}

}
