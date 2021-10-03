package com.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.entities.Customer;
import com.springboot.models.CustomerModel;
import com.springboot.models.CountryProperties;
import com.springboot.utils.PhoneValidator;

@Component
public class CustomerMapper {

  @Autowired
  private PhoneValidator phoneValidator;


  public CustomerModel fromEntity(Customer entity) {
    if (entity == null) {
      return null;
    }
    CustomerModel model = new CustomerModel();

    model.setId(entity.getId());
    model.setName(entity.getName());

    String phone = entity.getPhone();
    model.setPhone(phone);
    Optional<CountryProperties> country = phoneValidator.getCountryByPhone(phone);
    model.setValid(country.isPresent() && phoneValidator.isValidPhone(phone,country.get().getRegex()));
    country.ifPresent(c -> {
      model.setCountry(c.getCountry());
      model.setCountryCode(c.getCountryCode());
    });
    return model;
  }

}
