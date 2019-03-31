/**
 * 
 */
package com.revature.pojos;

import java.sql.Timestamp;

/**
 * Our general Reimbursement Class for Employee Reimbursement System containing:
 * Reimbursement Class 
 *
 * @author Aleksandar A.
 */
public class Reimbursement {
	private int id;
	private long amount;
	private Timestamp submittedTime;
	private Timestamp resolvedTime;
	private String description;
	private String author;
	private String resolver;
	private String status;
	private String type;
	
	
	public Reimbursement() {} //public no args constructor
	
	
	/*
	 * Reimbursement object constructor without id field
	 */
	public Reimbursement(long amount, Timestamp submittedTime, Timestamp resolvedTime, String description, String author,
			String resolver, String status, String type) {
		super();
		this.amount = amount;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	
	
	/*
	 * Reimbursement object constructor with id field
	 */
	public Reimbursement(int id, long amount, Timestamp submittedTime, Timestamp resolvedTime, String description,
			String author, String resolver, String status, String type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submittedTime = submittedTime;
		this.resolvedTime = resolvedTime;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Timestamp getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(Timestamp submittedTime) {
		this.submittedTime = submittedTime;
	}
	public Timestamp getResolvedTime() {
		return resolvedTime;
	}
	public void setResolvedTime(Timestamp resolvedTime) {
		this.resolvedTime = resolvedTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getResolver() {
		return resolver;
	}
	public void setResolver(String resolver) {
		this.resolver = resolver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submittedTime=" + submittedTime + ", resolvedTime="
				+ resolvedTime + ", description=" + description + ", author=" + author + ", resolver=" + resolver
				+ ", status=" + status + ", type=" + type + "]";
	}


	
	
	
	
}
