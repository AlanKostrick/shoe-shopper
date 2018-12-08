package org.wecancodeit.shopper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.shopper.models.User;

public interface UserRepository extends CrudRepository<User, Long> {


	Optional<User> findByUsername(String name);

}
