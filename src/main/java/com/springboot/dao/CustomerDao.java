package com.springboot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

  /**
   * find all customers that has phone starting with specific query
   * @param query String query matches the beginning characters of phone column
   * @param pageable Pageable for limiting the data returned
   * @return Page of customers
   */
  Page<Customer> findAllByPhoneStartingWith(String query,Pageable pageable);

  //Regex is not supported at spring jpa
  Page<Customer> findAllByPhoneMatches(String regex,Pageable pageable);


}
