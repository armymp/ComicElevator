package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class FullCollectionDTO {
	private String name;
	private long collectionID;
	private long userID;
	private boolean isPrivate;
	private List<ComicDTO> comics = new ArrayList<>();
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public List<ComicDTO> getComics() {
		return comics;
	}
	public void addComics(ComicDTO comic) {
		comics.add(comic);
	}
	public long getCollectionID() {
		return collectionID;
	}
	public void setCollectionID(long collectionID) {
		this.collectionID = collectionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
