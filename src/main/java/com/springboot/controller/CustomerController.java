package com.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.CustomerDao;
import com.springboot.entities.Customer;
import com.springboot.model.CustomerFilter;
import com.springboot.service.ICustomerService;

@RestController
public class CustomerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private ICustomerService customerService;

  private static final String DEFAULT_PAGE_COUNT = "10";

  @GetMapping("/hello")
  public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

  @GetMapping("/sortedcustomers")
  public Page<Customer> findAllCustomerSortedByName(@RequestParam(value = "page") int pageIndex,
      @RequestParam(value = "offset", defaultValue = DEFAULT_PAGE_COUNT) int offset) {
    if(offset <= 1){
      LOGGER.warn("Page size must not be less than one!");
      offset = Integer.valueOf(DEFAULT_PAGE_COUNT);
    }
    CustomerFilter filter = new CustomerFilter(pageIndex,offset);
    return customerService.getSortedCustomers(filter);
  }
}
