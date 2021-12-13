package main;

import java.util.Date;

//{
//	"wishListId":"97a5b1c08dea53", 
//	"name":"birthday wishlist", 
//	"userEmail":"dummy@s.afeka.ac.il",  
//	"createdTimestamp":"2021-12-09T13:38:23.104+0000"
//}

public class WishListBoundary {
	private String wishListId;
	private String name;
	private String userEmail;
	private Date createdTimestamp;

	public WishListBoundary() {
	}

	public String getWishListId() {
		return wishListId;
	}

	public void setWishListId(String wishListId) {
		this.wishListId = wishListId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
}
