package org.wecancodeit.shopper.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.shopper.models.Cart;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.repositories.CartRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;

@Controller
public class CartController {

	@Resource
	private CartRepository cartRepo;
	
	@Resource
	private ItemRepository itemRepo;
	

	@RequestMapping("/cart")
	public String findAllCartItems(Model model) {
		model.addAttribute("cartModel", cartRepo.findAll());
		return "cart";
	}

	@RequestMapping("/add-item-to-cart")
	public String addItemsToCartFromItemPage(@RequestParam(value = "id") long itemId) {
		Optional<Item> itemResult = itemRepo.findById(itemId);
		Item item = itemResult.get();
		Cart lineItem;

		Optional<Cart> foundItem = cartRepo.findByItem(item);

		if (foundItem.isPresent()) {
			lineItem = foundItem.get();
		} else {
			lineItem = new Cart(item);
		}

		cartRepo.save(lineItem);

		return "redirect:/cart";
	}
}
