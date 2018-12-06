package org.wecancodeit.shopper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.shopper.models.Cart;
import org.wecancodeit.shopper.models.Item;

public interface CartRepository extends CrudRepository<Cart, Long> {

	Optional<Cart> findByItem(Item item);

}
 