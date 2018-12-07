package org.wecancodeit.shopper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ItemRepository itemRepo;

	@Resource
	private CartItemRepository cartRepo;

	@Resource
	private UserRepository userRepo;

	@Test
	public void shouldSaveAndLoadAnItem() {

		Item item = new Item("item one", "", "image url");
		itemRepo.save(item);
		long itemId = item.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Item> result = itemRepo.findById(itemId);
		item = result.get();
		assertThat(item.getItemName(), is("item one"));
	}

	@Test
	public void shouldGenerateItemId() {
		Item item = new Item("item one", "", "image url");
		itemRepo.save(item);

		entityManager.flush();
		entityManager.clear();
		assertThat(item.getId(), is(greaterThan(0L)));

	}

	@Test
	public void shouldSaveAndLoadAnItemToTheCart() {
		Item item = new Item("Item Name", "", "");
		itemRepo.save(item);

		CartItem cart = new CartItem(item);
		cartRepo.save(cart);

		long cartId = cart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<CartItem> result = cartRepo.findById(cartId);
		cart = result.get();

		assertThat(cart.getItem().getItemName(), is("Item Name"));
		assertTrue(result.isPresent());
	}

	@Test
	public void shouldSaveAndLoadUser() {
		User adminUser = new User("admin", "admin", "ADMIN");
		userRepo.save(adminUser);
		
		long userId = adminUser.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<User> result = userRepo.findById(userId);
		adminUser = result.get();

		assertThat(adminUser.getUsername(), is("admin"));
		assertTrue(result.isPresent());
	}

}
