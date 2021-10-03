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
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;

@Service
public class CustomerServiceImpl implements ICustomerService {

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerMapper mapper;

  @Override
  public Page<CustomerModel> getSortedCustomers(@NotNull CustomerFilter filter) {
    Sort sort = Optional.ofNullable(filter.getCustomerSort()).orElse(Sort.unsorted());
    Pageable pageable = PageRequest.of(filter.getPageIndex(), filter.getItemsPerPage(), sort);
    return customerDao.findAll(pageable).map(customer -> mapper.fromEntity(customer));

  }

}
