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
import org.wecancodeit.shopper.models.Cart;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.repositories.CartRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ItemRepository itemRepo;

	@Resource
	private CartRepository cartRepo;

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

		Cart cart = new Cart(item);
		cartRepo.save(cart);

		long cartId = cart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Cart> result = cartRepo.findById(cartId);
		cart = result.get();

		assertThat(cart.getItem().getItemName(), is("Item Name"));
		assertTrue(result.isPresent());
	}

}
