package org.wecancodeit.shopper.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.shopper.models.CartItem;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.repositories.CartItemRepository;
import org.wecancodeit.shopper.repositories.ItemRepository;

@Controller
public class CartItemController {

	@Resource
	private CartItemRepository cartRepo;

	@Resource
	private ItemRepository itemRepo;

	@RequestMapping("/cart")
	public String findAllCartItems(Model model) {
		model.addAttribute("cartItemModel", cartRepo.findAll());
		return "cart";
	}

	@RequestMapping("/add-item-to-cart")
	public String addItemsToCartFromItemPage(@RequestParam(value = "id") long itemId) {

		Optional<Item> itemResult = itemRepo.findById(itemId);
		Item item = itemResult.get();
		CartItem lineItem;

		Optional<CartItem> foundItem = cartRepo.findByItem(item);

		if (foundItem.isPresent()) {
			lineItem = foundItem.get();
		} else {
			lineItem = new CartItem(item);
		}

		cartRepo.save(lineItem);

		return "redirect:/cart";
	}

}