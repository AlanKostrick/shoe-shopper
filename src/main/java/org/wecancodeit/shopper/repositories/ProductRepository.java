package org.wecancodeit.shopper.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.shopper.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
