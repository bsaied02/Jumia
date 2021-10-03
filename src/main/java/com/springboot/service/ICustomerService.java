package com.springboot.service;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.springboot.entities.Customer;
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;

public interface ICustomerService {

  Page<CustomerModel> getSortedCustomers(@NotNull CustomerFilter filter);

  Page<CustomerModel> getCustomersByCountry(@NotNull String countryName,@NotNull CustomerFilter filter);

  Page<CustomerModel> getValidCustomers(boolean isValid,@NotNull CustomerFilter filter);

  Page<CustomerModel> getCustomersByCountryAndValidity(boolean isValid,@NotNull String countryName,@NotNull CustomerFilter filter);
}
