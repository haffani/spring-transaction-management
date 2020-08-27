package com.affani.datatranx.model;

import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
* @author  Hamza AFFANI
* @version 1.0
* 
*/

@Entity
public class Account {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	private NumberFormat fmt = NumberFormat.getCurrencyInstance();
	
	@Column(name="rate")
	private final double RATE = 0.035; // interest rate of 3.5%
	
	private long acctNumber;
	private double balance;
	private String name;
	private String last_operation;
    

	public Account() {
		this.name = "N/A";
		this.acctNumber = 0;
		this.balance = 0.0;
	}

	public Account(String owner, long account, double initial) {
		this.name = owner;
		this.acctNumber = account;
		this.balance = initial;
	}
	
    public NumberFormat getFmt() {
		return fmt;
	}


	public void setFmt(NumberFormat fmt) {
		this.fmt = fmt;
	}


	public long getAcctNumber() {
		return acctNumber;
	}


	public void setAcctNumber(long acctNumber) {
		this.acctNumber = acctNumber;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLast_operation() {
		return last_operation;
	}


	public void setLast_operation(String last_operation) {
		this.last_operation = last_operation;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	@Override
	public String toString() {
		return "Account [id=" + id + ", fmt=" + fmt + ", RATE=" + RATE + ", acctNumber=" + acctNumber + ", balance="
				+ balance + ", name=" + name + ", last_operation=" + last_operation + "]";
	}
}
