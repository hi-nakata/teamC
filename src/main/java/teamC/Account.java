package teamC;

public class Account {
	private String loginId;
	private String loginPass;
	private boolean admin;
	private String userName;
	private boolean logined;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPass() {
		return loginPass;
	}
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isLogined() {
		return logined;
	}
	public void setLogined(boolean isLogined) {
		this.logined = isLogined;
	}
}
