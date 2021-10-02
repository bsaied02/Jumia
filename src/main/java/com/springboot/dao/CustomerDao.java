package com.springboot.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Customer;

@Repository
public interface CustomerDao extends PagingAndSortingRepository<Customer, Integer> {
}
