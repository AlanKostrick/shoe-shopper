package org.wecancodeit.shopper;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.shopper.controllers.CartItemController;
import org.wecancodeit.shopper.controllers.ImageUploadService;
import org.wecancodeit.shopper.controllers.ProductController;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ProductRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

	@Resource
	private ProductRepository productRepo;

	@Resource
	private ProductController productController;

	@Resource
	private CartItemRepository cartItemRepo;

	@Resource
	private CartItemController cartItemController;

	@Resource
	private ImageUploadService imageUploadService;
	
	@Resource
	private UserRepository userRepo;

	@Test
	public void contextLoads() throws Exception {
		assertThat(productRepo).isNotNull();
		assertThat(productController).isNotNull();
		assertThat(cartItemRepo).isNotNull();
		assertThat(cartItemController).isNotNull();
		assertThat(imageUploadService).isNotNull();
		assertThat(userRepo).isNotNull();

	}

}
