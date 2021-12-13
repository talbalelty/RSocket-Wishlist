package main;

import java.util.Arrays;

//{"sortBy":["userEmail", "wishListId"], "order":"ASC"}
//{"sortBy":["wishListId"], "order":"ASC"}
//{"sortBy":["productId"], "order":"DESC"}

public class SortBoundary {
	private String[] sortBy;
	private String order;

	public SortBoundary() {
	}

	public String[] getSortBy() {
		return sortBy;
	}

	public void setSortBy(String[] sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SortBoundary [sortBy=" + Arrays.toString(sortBy) + ", order=" + order + "]";
	}
}
