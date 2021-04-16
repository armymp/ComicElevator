package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.services.MarvelService;
@RestController
@CrossOrigin
public class MarvelController {
	

	@RequestMapping(value = "marvel", method = RequestMethod.GET)
	public ResponseEntity<String> searchMarvel(Principal principal,
			@RequestParam (required=false) String title,
			@RequestParam (required=false) Integer issue,
			@RequestParam (required=false) Integer page){
		//MarvelService.getComics();
	
		return MarvelService.getComics(title,issue,page);
	}
}
