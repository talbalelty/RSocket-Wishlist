package main;

//{"userEmail":"dummy@s.afeka.ac.il"}

public class UserBoundary {
	private String userEmail;

	public UserBoundary() {
		super();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "UserBoundary [userEmail=" + userEmail + "]";
	}
}
