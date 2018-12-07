package org.wecancodeit.shopper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.Item;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	Optional<CartItem> findByItem(Item item);

}
 