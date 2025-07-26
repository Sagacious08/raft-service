package com.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.repository.RaftRepository;

@Service
public class RaftService {

	private final RaftRepository raftRepository;
	
	@Autowired
	public RaftService(RaftRepository raftRepository) {
		this.raftRepository = raftRepository;
		
	}
	
	public String bookRaft() {
		
		return null;
	}
}
