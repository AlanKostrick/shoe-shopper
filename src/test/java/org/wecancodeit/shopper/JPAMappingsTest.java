package org.wecancodeit.shopper;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
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
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ProductRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CartItemRepository cartRepo;

	@Resource
	private UserRepository userRepo;

	@Test
	public void shouldSaveAndLoadAnProduct() {

		Product product = new Product("product one", "", "product url");
		productRepo.save(product);
		long productId = product.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Product> result = productRepo.findById(productId);
		product = result.get();
		assertThat(product.getProductName(), is("product one"));
	}

	@Test
	public void shouldGenerateProductId() {
		Product product = new Product("item one", "", "image url");
		productRepo.save(product);

		entityManager.flush();
		entityManager.clear();
		assertThat(product.getId(), is(greaterThan(0L)));

	}

	@Test
	public void shouldSaveAndLoadAProductToTheCart() {
		Product product = new Product("product name", "", "");
		productRepo.save(product);

		CartItem cart = new CartItem(product);
		cartRepo.save(cart);

		long cartId = cart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<CartItem> result = cartRepo.findById(cartId);
		cart = result.get();

		assertThat(cart.getProduct().getProductName(), is("product name"));
		assertTrue(result.isPresent());
	}
	
	@Test
	public void shouldSaveAndLoadProductsToTheCart() {
		Product product1 = new Product("product 1", "", "");
		productRepo.save(product1);
		Product product2 = new Product("product 2", "", "");
		productRepo.save(product2);

		CartItem cart = new CartItem(product1);
		cartRepo.save(cart);

		long cartId = cart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<CartItem> result = cartRepo.findById(cartId);
		cart = result.get();

		assertThat(cart.getProduct().getProductName(), is("product 1"));
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
	
	@Test
	public void shouldContainCartItemsForUser() {
		User adminUser = new User("admin", "admin", "ADMIN");
		userRepo.save(adminUser);
		User user = new User("user", "user", "USER");
		userRepo.save(user);
		
		Product product1 = new Product("product 1","","");
		productRepo.save(product1);
		Product product2 = new Product("product 2","","");
		productRepo.save(product2);
		Product product3 = new Product("product 3","","");
		productRepo.save(product3);
		
		CartItem item1 = new CartItem(product1, adminUser);
		cartRepo.save(item1);
		CartItem item2 = new CartItem(product2, adminUser);
		cartRepo.save(item2);
		CartItem item3 = new CartItem(product3, user);
		cartRepo.save(item3);
		
		long userId = adminUser.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<User> result = userRepo.findById(userId);
		adminUser = result.get();
		
		assertThat(adminUser.getCartItems(), containsInAnyOrder(item1,item2));
	}

}
