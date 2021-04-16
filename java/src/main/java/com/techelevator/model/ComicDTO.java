package com.techelevator.model;

public class ComicDTO {
	
	/*
	 * Storing the name of the comic as well as the issue number, id and publisher
	 * Creators is the artists and writers that worked on the comic
	 * Characters is the characters that appeared in the comic
	 * thumbnailLink is the full web path to the image for that comic.
	 * 
	 */
	
	private long id;
	private long marvelID;
	private String name;
	private int issueNumber;
	private String publisher;
	private String Series;
	private String[] creators;
	private String[] characters;
	private String thumbnailLink;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(int issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String[] getCreators() {
		return creators;
	}
	public void setCreators(String[] creators) {
		this.creators = creators;
	}
	public String[] getCharacters() {
		return characters;
	}
	public void setCharacters(String[] character) {
		this.characters = character;
	}
	public String getThumbnailLink() {
		return thumbnailLink;
	}
	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}
	public String getSeries() {
		return Series;
	}
	public void setSeries(String series) {
		Series = series;
	}
	public long getMarvelID() {
		return marvelID;
	}
	public void setMarvelID(long marvelID) {
		this.marvelID = marvelID;
	}
	
}
