package com.springboot.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;
import com.springboot.service.ICustomerService;

@RestController
@CrossOrigin
public class CustomerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private ICustomerService customerService;

  private static final int DEFAULT_PAGE_COUNT = 10;
  private static final String NAME_COLUMN = "name";

  @GetMapping("/hello")
  public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

  @GetMapping("/sortedcustomers")
  public Page<CustomerModel> findAllCustomerSortedByName(@RequestParam(value = "page") int pageIndex,
      @RequestParam(value = "itemsPerPage") int itemsPerPage, @RequestParam(value = "sort", required = false) String sortDirection) {
    if (pageIndex < 0) {
      LOGGER.warn("Page index must not be less than zero!");
      pageIndex = 0;
    }
    if (itemsPerPage <= 1) {
      LOGGER.warn("Page items must not be less than one!");
      itemsPerPage = DEFAULT_PAGE_COUNT;
    }
    Sort.Direction direction = Objects.equals("desc", sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort nameSort = Sort.by(direction,NAME_COLUMN);
    CustomerFilter filter = new CustomerFilter(pageIndex, itemsPerPage);
    return customerService.getSortedCustomers(filter);
  }
}
