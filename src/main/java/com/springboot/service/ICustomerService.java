package com.springboot.service;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.springboot.entities.Customer;
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;

public interface ICustomerService {
  /**
   * get all customers sorted
   * @param filter CustomerFilter for filtering & limiting the result set
   * @return Page of customers
   */
  Page<CustomerModel> getSortedCustomers(@NotNull CustomerFilter filter);

  /**
   * get all customers that there phones matches country code
   * @param countryName String name of the country
   * @param filter CustomerFilter for filtering & limiting the result set
   * @return Page of customers
   */
  Page<CustomerModel> getCustomersByCountry(@NotNull String countryName,@NotNull CustomerFilter filter);

  /**
   * get all customers according to their phones validity
   * @param isValid boolean indicating the validity status needed
   * @param filter CustomerFilter for filtering & limiting the result set
   * @return Page of customers
   */
  Page<CustomerModel> getValidCustomers(boolean isValid,@NotNull CustomerFilter filter);

  /**
   * get customers with combined filtering (phone number matches the country code & with specific validity status)
   * @param isValid boolean indicating the validity status needed
   * @param countryName String name of the country
   * @param filter CustomerFilter for filtering & limiting the result set
   * @return Page of customers
   */
  Page<CustomerModel> getCustomersByCountryAndValidity(boolean isValid,@NotNull String countryName,@NotNull CustomerFilter filter);
}
