package com.techelevator.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MarvelService {
	private static final String BASE_URL = "http://gateway.marvel.com/v1/public/comics";
	private static final RestTemplate restTemplate = new RestTemplate();
	private static final String privateKey = "9cd0c99674ed2f94e25da86556cfb98561fbf212";
	private static final String publicKey = "7aec7d32217f0db4509ce4580b67fc98";
	
	public static ResponseEntity<String> getComics(String title, Integer issue, Integer page){
		String time =  String.valueOf(System.currentTimeMillis());
		String combo = time+privateKey+publicKey;
	    String myHash = DigestUtils.md5Hex(combo);
	    String query = "&limit=100";
	    if(title!=null) {
	    	query += "&titleStartsWith="+title;
	    }
	    if(issue!=null) {
	    	query += "&issueNumber="+issue;
	    }
	    if(page!=null) {
	    	query += "&offset="+(page-1)*100;
	    }
	    ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL+"?apikey="+publicKey+"&ts="+time+"&hash="+myHash+query, String.class);
		return response;
	}
}
