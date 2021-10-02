package com.springboot.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.dao.CustomerDao;
import com.springboot.entities.Customer;
import com.springboot.model.CustomerFilter;

@Service
public class CustomerService implements ICustomerService {

  @Autowired
  private CustomerDao customerDao;

  @Override
  public Page<Customer> getSortedCustomers(@NotNull CustomerFilter filter) {
    Sort sort = Optional.ofNullable(filter.getCustomerSort()).orElse(Sort.unsorted());
    Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getOffset(), sort);
    return customerDao.findAll(pageable);
  }

}
