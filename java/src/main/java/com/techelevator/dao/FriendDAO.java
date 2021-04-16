package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.FriendDTO;
import com.techelevator.model.User;

@Service
public class FriendDAO {
	private JdbcTemplate jdbcTemplate;
	
	public FriendDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<FriendDTO> getAllFriendID() {

		List<FriendDTO> friendDTO = new ArrayList<>();
		String sqlFindFriendID = "SELECT f.* FROM friend f " +
				"WHERE fu.user_id = ? ORDER by f.friend_id ";
		SqlRowSet friendRow = jdbcTemplate.queryForRowSet(sqlFindFriendID);
		while (friendRow.next()) {
			friendDTO.add(mapRowToFriend(friendRow));
		}

		return friendDTO;
	}
	public List<User> getAllFriendsById(long id) {

		List<User> friends = new ArrayList<>();
		String sqlFindFriendID = "SELECT u.* FROM friend f INNER JOIN users u ON f.friend_id = u.user_id WHERE f.user_id = ?";
		SqlRowSet friendRow = jdbcTemplate.queryForRowSet(sqlFindFriendID,id);
		while (friendRow.next()) {
			friends.add(mapRowToUser(friendRow));
		}

		return friends;
	}
	public boolean addFriend(FriendDTO friend, int userID) {
		
		String sqlInsertFriend = "INSERT INTO friend (user_id, friend_id, status_id, status_id_desc) " +
								 "VALUES (?, ?, ?, ?)";
		
		try {
			
			jdbcTemplate.update(sqlInsertFriend,friend.getUserID(),
					friend.getFriendID(), friend.getStatusID(), friend.getStatusIDDesc());
			
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteFriend(int friendID, int userID) {
		String deleteFriend = "DELETE FROM friend WHERE friend_id =? AND user_id = ?";
		
		int success = jdbcTemplate.update(deleteFriend, friendID,userID);
		return (success>0)? true:false;
	}
		
	private FriendDTO mapRowToFriend(SqlRowSet friendRow) {
		FriendDTO friend = new FriendDTO();
		friend.setFriendID(friendRow.getInt("friend_id"));
		friend.setUserID(friendRow.getInt("user_id"));
		friend.setStatusID(friendRow.getInt("status_id"));
		friend.setStatusIDDesc(friendRow.getString("status_id_desc"));
		return friend;
	}
	 private User mapRowToUser(SqlRowSet rs) {
	        User user = new User();
	        user.setId(rs.getLong("user_id"));
	        user.setUsername(rs.getString("username"));
	        return user;
	    }
}




