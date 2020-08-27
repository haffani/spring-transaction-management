package com.affani.datatranx.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.affani.datatranx.model.Account;

/**
* @author  Hamza AFFANI
* @version 1.0
* 
*/

public interface IAccountDAO extends JpaRepository<Account, Long> {}