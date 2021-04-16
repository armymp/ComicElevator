package com.techelevator.model;



public class FriendDTO {
	
	private int iD;
	private int friendID;
	private int userID;
	private int statusID;
	private String statusIDDesc;
	
	
	public int getID() {
		return iD;
	}
	
	public void setID(int iD) {
		this.iD = iD;
	}
	public int getFriendID() {
		return friendID;
	}
	
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	
	public String getStatusIDDesc() {
		return statusIDDesc;
	}

	public void setStatusIDDesc(String statusIDDesc) {
		this.statusIDDesc = statusIDDesc;
	}


}
