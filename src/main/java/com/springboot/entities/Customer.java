package com.springboot.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Customer {

  @Id
  private Integer id;
  @Column
  private String name;
  @Column
  private String phone;

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
