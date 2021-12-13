package main;

//{
//	"wishListId":"97a5b1c08dea53", 
//	"productId":"p42"
//}

public class WishListProductBoundary {
	private String wishListId;
	private String productId;

	public WishListProductBoundary() {
		super();
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
		return "WishListProductBoundary [wishListId=" + wishListId + ", productId=" + productId + "]";
	}

}
