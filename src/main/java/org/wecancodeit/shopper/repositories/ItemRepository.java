package org.wecancodeit.shopper.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.shopper.models.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
