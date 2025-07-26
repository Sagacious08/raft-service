package com.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.service.RaftService;

@RestController
@RequestMapping("/api/v1/raft")
public class RaftController {
	
	private final RaftService raftService;
	
	@Autowired
	public RaftController(RaftService raftService) {
		this.raftService = raftService;
		
	}

	@PostMapping("/book")
	public ResponseEntity<?> createRaftBooking(){
		
		try {
			raftService.bookRaft();
			
			return new ResponseEntity<>("",HttpStatus.OK);
		}catch(Exception ex) {
			
			return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
		}
	}
}
