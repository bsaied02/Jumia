package com.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.dao.CustomerDao;
import com.springboot.entities.Customer;
import com.springboot.models.CountryProperties;
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;
import com.springboot.utils.PhoneValidator;

@Service
public class CustomerServiceImpl implements ICustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerMapper mapper;

  @Autowired
  private PhoneValidator phoneValidator;

  @Override
  public Page<CustomerModel> getSortedCustomers(@NotNull CustomerFilter filter) {
    return customerDao.findAll(getPageableFromFilter(filter))
        .map(customer -> mapper.fromEntity(customer));

  }

  @Override
  public Page<CustomerModel> getCustomersByCountry(@NotNull String countryName, @NotNull CustomerFilter filter) {
    Optional<CountryProperties> countryProperty = getCountryPropByCountryName(countryName);
    if (!countryProperty.isPresent()) {
      return Page.empty();
    }
    String countryCode = "(" + countryProperty.get().getCountryCode().replaceAll("[^0-9]", "") + ")";
    return customerDao.findAllByPhoneStartingWith(countryCode, getPageableFromFilter(filter))
        .map(customer -> mapper.fromEntity(customer));
  }

  @Override
  public Page<CustomerModel> getValidCustomers(boolean isValid, @NotNull CustomerFilter filter) {
    List<Customer> customers = filter.getCustomerSort() != null?
        customerDao.findAll(filter.getCustomerSort()): customerDao.findAll();
    List<CustomerModel> validCustomers = customers.stream()
        .map(customer -> mapper.fromEntity(customer))
        .filter(model -> isValid == model.isValid())
        .collect(Collectors.toList());
    return new PageImpl<>(
        validCustomers.stream()
        .skip(filter.getPageIndex()* filter.getItemsPerPage())
        .limit(filter.getItemsPerPage())
        .collect(Collectors.toList()),
        getPageableFromFilter(filter),
        validCustomers.size());
  }

  @Override
  public Page<CustomerModel> getCustomersByCountryAndValidity(boolean isValid,
      @NotNull String countryName,
      @NotNull CustomerFilter filter) {
    Optional<CountryProperties> countryProperty = getCountryPropByCountryName(countryName);
    if (!countryProperty.isPresent()) {
     return Page.empty();
    }
    List<CustomerModel> countryPage = getCustomersByCountry(countryName,filter).getContent();
    return new PageImpl<>(countryPage.stream()
    .filter(model -> isValid == model.isValid()).collect(Collectors.toList()));
  }

  private Optional<CountryProperties> getCountryPropByCountryName(@NotNull String countryName) {
    Optional<CountryProperties> countryProperty = phoneValidator.getCountryByCountryName(countryName);
    if (!countryProperty.isPresent()) {
      LOGGER.warn("No valid country found for country name: {}", countryName);
      return Optional.empty();
    }
    return countryProperty;
  }

  private Pageable getPageableFromFilter(@NotNull CustomerFilter filter) {
    Sort sort = Optional.ofNullable(filter.getCustomerSort()).orElse(Sort.unsorted());
    return PageRequest.of(filter.getPageIndex(), filter.getItemsPerPage(), sort);
  }
}
