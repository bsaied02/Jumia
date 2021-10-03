package com.springboot.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import com.springboot.utils.PhoneValidator;

@RestController
@CrossOrigin
public class CustomerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private ICustomerService customerService;

  @Autowired
  private PhoneValidator phoneValidator;

  private static final int DEFAULT_PAGE_COUNT = 10;
  private static final String DEFAULT_SORT_COLUMN = "name";

  @GetMapping("/hello")
  public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

  @GetMapping("/allcustomers")
  public Page<CustomerModel> findAllCustomer(
      @RequestParam(value = "page") int pageIndex,
      @RequestParam(value = "itemsPerPage") int itemsPerPage,
      @RequestParam(value = "sortBy", required = false) String sortBy,
      @RequestParam(value = "sortDesc", required = false) boolean sortDesc,
      @RequestParam(value = "countryName", required = false) String countryNameFilter,
      @RequestParam(value = "validity", required = false) Boolean validityFilter) {
    if (pageIndex < 0) {
      LOGGER.warn("Page index must not be less than zero!");
      pageIndex = 0;
    }
    if (itemsPerPage <= 1) {
      LOGGER.warn("Page items must not be less than one!");
      itemsPerPage = DEFAULT_PAGE_COUNT;
    }
    Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
    String sortByColumn = StringUtils.isEmpty(sortBy) ? DEFAULT_SORT_COLUMN : sortBy;
    Sort sort = Sort.by(direction, sortByColumn);
    CustomerFilter filter = new CustomerFilter(pageIndex - 1, itemsPerPage, sort);
    if(!StringUtils.isEmpty(countryNameFilter) && validityFilter != null){
     return customerService.getCustomersByCountryAndValidity(validityFilter,countryNameFilter,filter);
    }
    if(!StringUtils.isEmpty(countryNameFilter)) {
     return customerService.getCustomersByCountry(countryNameFilter, filter);
    }
    if(validityFilter != null){
      return customerService.getValidCustomers(validityFilter,filter);
    }
    return customerService.getSortedCustomers(filter);
  }

  @GetMapping("/Countries")
  public List<String> getCountries() {
    return phoneValidator.getAllCountriesNames();
  }
}
