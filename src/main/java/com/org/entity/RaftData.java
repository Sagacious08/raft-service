package com.org.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * RaftData is an entity class representing a record in the "onl_raft" table.
 * This class is annotated with JPA annotations to map its fields to the table's columns.
 * It includes fields for the raft name, a unique identifier, and a code associated with the raft.
 * The class supports typical getter and setter methods and includes annotations for
 * auto-generation of a primary key value and mapping to the database table.
 */
@Entity
@Table(name = "onl_raft")
public class RaftData {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String rname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
}
