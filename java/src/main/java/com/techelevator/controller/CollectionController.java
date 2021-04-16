package com.techelevator.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.CollectionDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.CollectionDTO;
import com.techelevator.model.ComicDTO;
import com.techelevator.model.FullCollectionDTO;
import com.techelevator.model.StatDTO;
import com.techelevator.model.User;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CollectionController {
	private CollectionDAO collectionDAO;
	private UserDAO userDAO;
	public CollectionController(CollectionDAO collectionDAO, UserDAO userDAO) {
		this.collectionDAO = collectionDAO;
		this.userDAO = userDAO;
	}

	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collection/", method = RequestMethod.GET)
	public List<CollectionDTO> getAllCollections(Principal principal){
		if(principal!=null) {
			return collectionDAO.getAllCollectionList();
		}else {
			return collectionDAO.getAllPublicCollectionList();
		}
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "user/", method = RequestMethod.GET)
	public List<User> getAllUsers(){
		return userDAO.findAll();
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "user/{user}", method = RequestMethod.GET)
	public User getAllUsers(@PathVariable String user){
		return userDAO.findByUsername(user);
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "user/id/{id}", method = RequestMethod.GET)
	public User getAllUsers(@PathVariable long id){
		return userDAO.getUserById(id);
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collection/user/{user}", method = RequestMethod.GET )
    public List<CollectionDTO> getCollections(@PathVariable String user , Principal principal) {
		//userID of -1 is a anon user
		int userID = -1;
		if(principal != null) {
			userID = userDAO.findIdByUsername(principal.getName());
		}
		System.out.println(userID);
		int collectionUserID = -1;
		try {
			collectionUserID = userDAO.findIdByUsername(user);
		}catch(RuntimeException rex) {
			collectionUserID = -1;
		}
		
    	return collectionDAO.getCollectionList(userID,collectionUserID);
    }
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collection/{id}", method = RequestMethod.GET )
    public FullCollectionDTO getCollection(@PathVariable long id , Principal principal) {
		//userID of -1 is a anon user
		int userID = -1;
		if(principal != null) {
			userID = userDAO.findIdByUsername(principal.getName());
		}
    	return collectionDAO.getCollection(id,userID);
    }
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "userslike/{user}", method = RequestMethod.GET )
	public List<User> getUsersLike(@PathVariable String user, Principal principal){
		return userDAO.findLike(user);
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collectionslike/{name}", method = RequestMethod.GET )
	public List<CollectionDTO> getCollectionsLike(@PathVariable String name, Principal principal){
		return collectionDAO.SearchPublicCollections(name);
	}
	//Creates a blank collection is my guess, maybe it can pass in a list of comics already attached but tbh I don't think that is how I will handle it.
	@RequestMapping(value = "collection/", method = RequestMethod.POST )
    public boolean addCollection(@RequestBody CollectionDTO collection, Principal principal) {

		int userID = userDAO.findIdByUsername(principal.getName());
    	return collectionDAO.addCollection(collection,userID);
    }
	
	/*
	 * This will just update the name and the privacy settings of the Collection
	 */
	@RequestMapping(value = "collection/{id}", method = RequestMethod.PUT )
    public boolean updateCollection(@RequestBody CollectionDTO collection, @PathVariable long id ,Principal principal) {
		int userID = userDAO.findIdByUsername(principal.getName());
    	return collectionDAO.updateCollection(collection,userID,id);
    }
	
    //Deletes the whole collection and removes references to comics, if the comics are orphaned then we remove the comics from our db
	@RequestMapping(value = "collection/{id}", method = RequestMethod.DELETE)
    public boolean deleteCollection(@PathVariable long id  ,Principal principal) {
		int userID = userDAO.findIdByUsername(principal.getName());
    	return collectionDAO.deleteCollection(id,userID);
	}
	//Anyone can look at comic details? Might end up unused since we can get better info from the Marvel API
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "comic/{id}", method = RequestMethod.GET)
	public ComicDTO getComic(@PathVariable long id) {
		return collectionDAO.getComic(id);
	}
	
	
	//Removes a comic from the Collection and if the comic is orphaned and no longer referenced removes it's data as well
	@RequestMapping(value = "collection/{collectionID}/comic/{comicID}", method = RequestMethod.DELETE)
	public boolean removeComic(@PathVariable long collectionID, @PathVariable long comicID,Principal principal) {
		int userID = userDAO.findIdByUsername(principal.getName());
	
		return collectionDAO.removeComic(collectionID,comicID,userID);
	}
	//Add a comic to the collection, checking first if there is information on the comic before adding it to our table.
	@RequestMapping(value = "collection/{collectionID}/comic/", method = RequestMethod.POST)
	public boolean addComic(@PathVariable long collectionID, @RequestBody ComicDTO comic,Principal principal) {
		int userID = userDAO.findIdByUsername(principal.getName());
		return collectionDAO.addComicToCollection(collectionID,comic,userID);
	}
	@RequestMapping(value = "collection/{id}/search/",method = RequestMethod.GET)
	public List<ComicDTO> searchComicCollection(Principal principal,@PathVariable long id,
			@RequestParam (required=false) List<String> characters,
			@RequestParam (required=false) List<String> creators,
			@RequestParam (required=false) String publisher,
			@RequestParam (required=false) String series,
			@RequestParam (required=false) String title,
			@RequestParam (required=false) Integer issue){
		
		int userID = -1;
		if(principal!=null) {
			userID =  userDAO.findIdByUsername(principal.getName());
		}
		FullCollectionDTO collection = collectionDAO.getCollection( id,userID);
		
		if(collection!=null) {
			return acquireFilteredComics(collection.getComics(),characters,creators,publisher,series,title,issue);
		}else {
			return null;
		}
	}
	@RequestMapping(value = "comic/search/",method = RequestMethod.GET)
	public List<ComicDTO> searchComics(Principal principal,
			@RequestParam (required=false) List<String> characters,
			@RequestParam (required=false) List<String> creators,
			@RequestParam (required=false) String publisher,
			@RequestParam (required=false) String series,
			@RequestParam (required=false) String title,
			@RequestParam (required=false) Integer issue){

		List<ComicDTO> comics = collectionDAO.getAllComics();
		if(comics!=null) {
			return acquireFilteredComics(comics,characters,creators,publisher,series,title,issue);
		}else {
			return null;
		}
	}
	@RequestMapping(value = "collection/{id}/stat/",method = RequestMethod.GET)
	public int statComicCollection(Principal principal,@PathVariable long id,
			@RequestParam (required=false) List<String> characters,
			@RequestParam (required=false) List<String> creators,
			@RequestParam (required=false) String publisher,
			@RequestParam (required=false) String series,
			@RequestParam (required=false) String title,
			@RequestParam (required=false) Integer issue){
		
		int userID = -1;
		if(principal!=null) {
			userID =  userDAO.findIdByUsername(principal.getName());
		}
		FullCollectionDTO collection = collectionDAO.getCollection( id,userID);
		if(collection!=null) {
			return acquireFilteredComics(collection.getComics(),characters,creators,publisher,series,title,issue).size();
		}else {
			return 0;
		}
	}
	@RequestMapping(value = "comic/stat/",method = RequestMethod.GET)
	public int statComics(Principal principal,
			@RequestParam (required=false) List<String> characters,
			@RequestParam (required=false) List<String> creators,
			@RequestParam (required=false) String publisher,
			@RequestParam (required=false) String series,
			@RequestParam (required=false) String title,
			@RequestParam (required=false) Integer issue){

		List<ComicDTO> comics = collectionDAO.getAllComics();
		if(comics!=null) {
			return acquireFilteredComics(comics,characters,creators,publisher,series,title,issue).size();
		}else {
			return 0;
		}
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "comic/{username}/topCharacters/",method = RequestMethod.GET)
	public List<StatDTO> statCharacterComics(Principal principal, @PathVariable String username){

		return collectionDAO.getTopCharacters(userDAO.findIdByUsername(username));
		
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "comic/{username}/topCreator/",method = RequestMethod.GET)
	public List<StatDTO> statCreatorComics(Principal principal, @PathVariable String username){

		return collectionDAO.getTopCreators(userDAO.findIdByUsername(username));
		
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "comic/topCharacters/",method = RequestMethod.GET)
	public List<StatDTO> statAllCharacterComics(Principal principal){

		return collectionDAO.getAllTopCharacters();
		
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "comic/topCreators/",method = RequestMethod.GET)
	public List<StatDTO> statAllCreatorComics(Principal principal){

		return collectionDAO.getAllTopCreators();
		
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collection/{id}/topCharacters/",method = RequestMethod.GET)
	public List<StatDTO> statCollectionCharacters(Principal principal, @PathVariable int id){

		return collectionDAO.getTopCharactersByCollection(id);
		
	}
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "collection/{id}/topCreators/",method = RequestMethod.GET)
	public List<StatDTO> statCollectionCreator(Principal principal, @PathVariable int id){

		return collectionDAO.getTopCreatorsByCollection(id);
		
	}
	public List<ComicDTO> acquireFilteredComics(List<ComicDTO> comics,List<String> characters,List<String> creators, String publisher, String series, String title, Integer issue){
		List<ComicDTO> filteredComics = new ArrayList<>();
		for(ComicDTO comic : comics) {
			//THIS IS HELLA CURSED
			if( (publisher==null||comic.getPublisher().toLowerCase().equals(publisher.toLowerCase()))&&
				(series==null||comic.getSeries().toLowerCase().equals(series.toLowerCase()))&&
				(title==null||comic.getName().toLowerCase().equals(title.toLowerCase()))&&
				(issue==null||comic.getIssueNumber()==(issue.intValue()))&&
				(creators==null||listContainsWords(comic.getCreators(),creators))&&
				(characters==null||listContainsWords(comic.getCharacters(),characters))
				) {
				
				filteredComics.add(comic);
			}
		}
		return filteredComics;
	}
	
	public boolean listContainsWords(String[] list, List<String> wordsToSearch) {
		boolean found;
		for(String searchWord : wordsToSearch) {
			found = false;
			for(String word : list) {
				if(word.toLowerCase().equals(searchWord.toLowerCase())) {
					found = true;
				}
			}
			if(!found) {
				return false;
			}
		}
		return true;
	}
}
