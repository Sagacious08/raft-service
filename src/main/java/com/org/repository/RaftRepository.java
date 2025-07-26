package com.org.repository;

import com.org.entity.RaftData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaftRepository extends JpaRepository<RaftData,Integer> {
}
