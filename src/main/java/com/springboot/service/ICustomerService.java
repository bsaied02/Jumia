package com.springboot.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.springboot.entities.Customer;
import com.springboot.model.CustomerFilter;
import com.springboot.model.CustomerModel;

public interface ICustomerService {

  Page<Customer> getSortedCustomers(@NotNull CustomerFilter filter);
}
