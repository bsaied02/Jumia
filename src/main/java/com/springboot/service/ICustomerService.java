package com.springboot.service;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.springboot.entities.Customer;
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;

public interface ICustomerService {

  Page<CustomerModel> getSortedCustomers(@NotNull CustomerFilter filter);
}
