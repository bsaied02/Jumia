package com.springboot;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.dao.CustomerDao;
import com.springboot.entities.Customer;

@RunWith(SpringRunner.class)
public class AbstractBaseTest {

  @Autowired
  private CustomerDao customerDao;


  @Before
  public void setUp() {
    //Given
    Customer customer1 = createCustomer(1, "c_customer", "(212) 691933626"); // valid    Morocco
    Customer customer2 = createCustomer(2, "e_customer", "(258) 042423566"); // invalid  Mozambique
    Customer customer3 = createCustomer(3, "d_customer", "(256) 7503O6263"); // invalid  Uganda
    Customer customer4 = createCustomer(4, "b_customer", "(251) 914701723"); // valid    Ethiopia
    Customer customer5 = createCustomer(5, "a_customer", "(237) 697151594"); // valid    Cameroon
    //When save
    customerDao.saveAll(Lists.newArrayList(customer1, customer2, customer3, customer4, customer5));
  }

  private Customer createCustomer(int id, String name, String phone) {
    Customer customer = new Customer();
    customer.setId(id);
    customer.setName(name);
    customer.setPhone(phone);
    return customer;
  }


  @Before
  public void tearDown() {

  }

}
