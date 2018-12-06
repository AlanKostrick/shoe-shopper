package org.wecancodeit.shopper.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Item {

	private String itemName;
	private String imageUrl;
	
	@Lob
	private String itemDescription;

	@Id
	@GeneratedValue
	private long id;

	public String getItemName() {
		return itemName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public long getId() {
		return id;
	}

	protected Item() {
	};

	public Item(String itemName, String itemDescription, String imageUrl) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
