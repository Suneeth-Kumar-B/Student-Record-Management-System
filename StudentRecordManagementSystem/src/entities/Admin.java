package entities;

public class Admin {
	int loginId;
	String name;
	String username;
	String password;
	public Admin(String name,String username, String password) {
		super();
		this.name=name;
		this.username = username;
		this.password = password;
	}
	
	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
