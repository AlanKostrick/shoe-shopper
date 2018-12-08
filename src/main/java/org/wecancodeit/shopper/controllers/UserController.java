package org.wecancodeit.shopper.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.models.UserNotFoundException;
import org.wecancodeit.shopper.repositories.UserRepository;

@Controller
public class UserController {
	
	@Resource
	private UserRepository userRepo;
	
	@RequestMapping(value = { "/findUser" }, method = RequestMethod.GET)
	public ModelAndView grabUser(Model model, Principal principal) {
		String loggedUser = principal.getName().toString();
		Optional<User> user = userRepo.findByUsername(loggedUser);
		long userId = user.get().getId();
		return new ModelAndView("redirect:/user?id=" + userId);
	}

	@RequestMapping("/user")
	public String findOneUser(@RequestParam(value = "id") long userId, Model model) throws UserNotFoundException {
		Optional<User> foundUser = userRepo.findById(userId);
		if (foundUser.isPresent()) {
			model.addAttribute("userModel", foundUser.get());
			return "user";
		}

		throw new UserNotFoundException();
	}

}
