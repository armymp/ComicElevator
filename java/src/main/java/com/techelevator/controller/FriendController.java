package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.FriendDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.FriendDTO;
import com.techelevator.model.User;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class FriendController {
	private FriendDAO friendDAO;
	private UserDAO userDAO;
	public FriendController(FriendDAO friendDAO, UserDAO userDAO) {
		this.friendDAO = friendDAO;
		this.userDAO = userDAO;
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "friend/", method = RequestMethod.GET)
		public List<FriendDTO> getAllFriends(Principal principal){
		if(principal != null) {
			return friendDAO.getAllFriendID();
		}	else {
			return null;
		}
	}

	@PreAuthorize("permitAll()")
	@RequestMapping(value = "friend/{userID}", method = RequestMethod.GET)
		public List<User> getAllFriendbyIDs(Principal principal,@PathVariable long userID){
		if(principal != null) {
			return friendDAO.getAllFriendsById(userID);
		}	else {
			return null;
		}
	}

	@RequestMapping( value = "friend/", method = RequestMethod.POST )
	public boolean addFriend(@RequestBody FriendDTO friendID, Principal principal ) {
		
		int userID = userDAO.findIdByUsername(principal.getName());
		return friendDAO.addFriend(friendID, userID);
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping( value = "/friend/{friendid}", method = RequestMethod.DELETE)
		public boolean deleteFriend(@PathVariable int friendid, Principal principal) {
		int friendID = userDAO.findIdByUsername(principal.getName());
		return friendDAO.deleteFriend(friendid, friendID);
	}
}
	


