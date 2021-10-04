package com.springboot.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO model
 */
@Data
@NoArgsConstructor
public class CustomerModel {

  private int id;
  private String name;
  private String phone;
  private String country;
  private String countryCode;
  private boolean valid;

}
