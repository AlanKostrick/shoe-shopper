package org.wecancodeit.shopper.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.wecancodeit.shopper.models.Item;
import org.wecancodeit.shopper.models.ItemNotFoundException;
import org.wecancodeit.shopper.repositories.ItemRepository;
import org.wecancodeit.shopper.repositories.UserRepository;

@Controller
public class ItemController {

	@Resource
	private ItemRepository itemRepo;

	@Resource
	private ImageUploadService uploader;
	
	@Resource
	private UserRepository userRepo;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/item")
	public String findOneItem(@RequestParam(value = "id") long itemId, Model model) throws ItemNotFoundException {
		
		Optional<Item> foundItem = itemRepo.findById(itemId);
		if (foundItem.isPresent()) {
			model.addAttribute("itemModel", foundItem.get());
			return "item";
		}
		throw new ItemNotFoundException();
	}
	
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("itemsModel", itemRepo.findAll());
		return "items";
	}
	

	@RequestMapping("/items")
	public String findAllItems(Model model) {
		model.addAttribute("itemsModel", itemRepo.findAll());
		return "items";

	}


	@PostMapping("/add-item")
	public String addItemWithImage(@RequestParam(value = "itemName", required = true) String itemName, @RequestParam(value = "itemDescription", required = true) String itemDescription,
			@RequestParam("imageFile") MultipartFile imageFile) throws Exception {

		String virtualFileUrl = uploader.uploadMultipartFile(imageFile);
		System.out.println(itemName);
		itemRepo.save(new Item(itemName, itemDescription, "/uploads/" + virtualFileUrl));

		return "redirect:/items";
	}

	@GetMapping("/uploads/{file:.+}") // allows "." to be part of PathVariable instead of truncating it
	public void serveImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("file") String fileName) throws Exception {

		// Resolve path of requested file
		File requestedFile = uploader.getUploadedFile(fileName);

		// Ensure requested item exists and is a file
		if (!requestedFile.exists() || !requestedFile.isFile()) {
			throw new Exception();
		}

		// Determine and set correct content type of response
		String fileContentType = Files.probeContentType(requestedFile.toPath());
		response.setContentType(fileContentType);

		// Serve file by streaming it directly to the response
		InputStream in = new FileInputStream(requestedFile);
		IOUtils.copy(in, response.getOutputStream());
	}

}
