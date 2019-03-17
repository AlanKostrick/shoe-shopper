package org.wecancodeit.shopper.config;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.UserRepository;

public class UserDetailsServiceImp implements UserDetailsService {

	@Resource
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
		// Get existing user from database
		User user;
		Optional<User> existingUser = userRepo.findByUsername(username);
		if (!existingUser.isPresent()) {
			throw new UsernameNotFoundException("User not found.");
		}
		user = existingUser.get();
		
		UserBuilder builder;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(user.getPassword());
		builder.roles(user.getRole());
		

		return builder.build();
	}

}
