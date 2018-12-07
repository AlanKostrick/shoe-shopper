package org.wecancodeit.shopper.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartItem {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Item item;

	public CartItem() {

	}

	public CartItem(Item item) {
		this.item = item;
	}

	public long getId() {

		return id;
	}

	public Item getItem() {

		return item;
	}
	

}
