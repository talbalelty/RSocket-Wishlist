package main;

import org.springframework.data.mongodb.core.mapping.Document;

//{
//	"wishListId":"97a5b1c08dea53", 
//	"productId":"p42"
//}

@Document(collection = "PRODUCTS")
public class WishListProductEntity {
	private String wishListId;
	private String productId;

	public WishListProductEntity() {
	}

	public String getWishListId() {
		return wishListId;
	}

	public void setWishListId(String wishListId) {
		this.wishListId = wishListId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "WishListProductEntity [wishListId=" + wishListId + ", productId=" + productId + "]";
	}

}
