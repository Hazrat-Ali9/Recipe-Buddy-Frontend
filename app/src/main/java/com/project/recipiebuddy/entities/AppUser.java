package com.project.recipiebuddy.entities;

public class AppUser {
	int user_id;
	String user_name, user_email,user_password;
	private static AppUser instance;

	public static AppUser getInstance() {
		if(instance==null){
			return  new AppUser();
		}
		return instance;
	}

	public static void setInstance(AppUser instance) {
		AppUser.instance = instance;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	@Override
	public String toString() {
		return "AppUser{" +
				"user_id=" + user_id +
				", user_name='" + user_name + '\'' +
				", user_email='" + user_email + '\'' +
				", user_password='" + user_password + '\'' +
				'}';
	}
}
