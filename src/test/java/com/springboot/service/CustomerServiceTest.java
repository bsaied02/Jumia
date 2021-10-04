package com.springboot.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import com.springboot.AbstractBaseTest;
import com.springboot.CustomerJpaConfig;
import com.springboot.models.CustomerFilter;
import com.springboot.models.CustomerModel;

@ContextConfiguration(classes = { CustomerJpaConfig.class })
@DataJpaTest
public class CustomerServiceTest extends AbstractBaseTest {

  @Autowired
  private ICustomerService customerService;


  @Test
  public void testGetSortedCustomers() {
    //Then
    List<CustomerModel> models = customerService.getSortedCustomers(getFilter()).getContent();
    Assert.assertFalse(models.isEmpty());
    Assert.assertEquals(5, models.size());
    Assert.assertEquals("a_customer", models.stream().findFirst().get().getName());
  }

  @Test
  public void testGetCustomersByCountry() {
    //Then
    List<CustomerModel> models = customerService.getCustomersByCountry("Cameroon", getFilter()).getContent();
    Assert.assertFalse(models.isEmpty());
    Assert.assertEquals(1, models.size());
    CustomerModel model = models.stream().findFirst().get();
    Assert.assertEquals("a_customer", model.getName());
    Assert.assertTrue(model.isValid());
  }

  @Test
  public void testGetValidCustomers() {
    //Then
    List<CustomerModel> validModels = customerService.getValidCustomers(true, getFilter()).getContent();
    Assert.assertFalse(validModels.isEmpty());
    Assert.assertEquals(3, validModels.size());
    Assert.assertEquals("a_customer", validModels.stream().findFirst().get().getName());

    List<CustomerModel> invalidModels = customerService.getValidCustomers(false, getFilter()).getContent();
    Assert.assertFalse(invalidModels.isEmpty());
    Assert.assertEquals(2, invalidModels.size());
    Assert.assertEquals("d_customer", invalidModels.stream().findFirst().get().getName());
  }

  @Test
  public void testGetCustomersByCountryAndValidity() {
    //Then
    String country = "Ethiopia";
    List<CustomerModel> models = customerService.getCustomersByCountryAndValidity(true, country, getFilter()).getContent();
    Assert.assertFalse(models.isEmpty());
    Assert.assertEquals(1, models.size());
    Assert.assertEquals("b_customer", models.stream().findFirst().get().getName());

    models = customerService.getCustomersByCountryAndValidity(false, country, getFilter()).getContent();
    Assert.assertTrue(models.isEmpty());

    country = "Uganda";
    models = customerService.getCustomersByCountryAndValidity(false, country, getFilter()).getContent();
    Assert.assertFalse(models.isEmpty());
    Assert.assertEquals(1, models.size());
    Assert.assertEquals("d_customer", models.stream().findFirst().get().getName());

    models = customerService.getCustomersByCountryAndValidity(true, country, getFilter()).getContent();
    Assert.assertTrue(models.isEmpty());
  }

  private CustomerFilter getFilter() {
    return new CustomerFilter(0, 5, Sort.by(Sort.Direction.ASC, "name"));
  }

}
