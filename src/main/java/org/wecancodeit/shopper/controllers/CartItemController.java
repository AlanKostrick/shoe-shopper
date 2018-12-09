package org.wecancodeit.shopper.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.CartNotFoundException;
import org.wecancodeit.shopper.models.Product;
import org.wecancodeit.shopper.models.User;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ProductRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@Controller
public class CartItemController {

	@Resource
	private CartItemRepository cartItemRepo;

	@Resource
	private ProductRepository productRepo;

	@Resource
	private UserRepository userRepo;

	@RequestMapping("/cart")
	public String findAllCartItems(Model model, Principal principal) throws CartNotFoundException {

		String loggedUser = principal.getName().toString();
		Optional<User> foundUser = userRepo.findByUsername(loggedUser);

		if (foundUser.isPresent()) {
			model.addAttribute("cartItemsModel", cartItemRepo.findAll());
			model.addAttribute("userModel", foundUser.get());
			return "cart";
		}
		throw new CartNotFoundException();
	}

	//for testing purposes only to pull all cart items
	public String findAllCartItems(Model model) throws CartNotFoundException {
		model.addAttribute("cartItemsModel", cartItemRepo.findAll());
		return "cart";

	}

	@RequestMapping("/add-product-to-cart")
	public String addItemsToCartFromProductPage(@RequestParam(value = "id") long productId, Principal principal,
			Model model) {

		String loggedUser = principal.getName().toString();
		Optional<User> foundUser = userRepo.findByUsername(loggedUser);

		User user;

		if (foundUser.isPresent()) {
			user = foundUser.get();
			model.addAttribute("userModel", user);

			Optional<Product> productResult = productRepo.findById(productId);
			Product product = productResult.get();
			CartItem lineItem;

			Optional<CartItem> foundItem = cartItemRepo.findByProduct(product);

			if (foundItem.isPresent()) {
				lineItem = foundItem.get();
			} else {
				lineItem = new CartItem(product, user);
			}
			cartItemRepo.save(lineItem);
		}

		return "redirect:/cart";
	}

	@RequestMapping("/delete-all")
	public String deleteAllProducts() {
		cartItemRepo.deleteAll();
		return "redirect:/cart";
	}

	@RequestMapping("/place-order")
	public String orderProducts() {
		cartItemRepo.deleteAll();
		return "redirect:/cart";
	}

}
