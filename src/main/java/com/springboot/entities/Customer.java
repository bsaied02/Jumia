package com.springboot.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

  @Id
  private Integer id;
  @Column
  private String name;
  @Column
  private String phone;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Customer customer = (Customer) o;
    return id.equals(customer.id) &&
        name.equals(customer.name) &&
        phone.equals(customer.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, phone);
  }
}
