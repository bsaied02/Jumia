package com.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.springboot.AbstractBaseTest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CustomerControllerTest extends AbstractBaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testFindAllCustomerWithValidParams() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/app/customers?page=1&itemsPerPage=10&sortBy=phone")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value("5"));
  }

  @Test(expected = org.springframework.web.util.NestedServletException.class)
  public void testFindAllCustomerWithInValidParams() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/app/customers?page=1&itemsPerPage=10&sortBy=invalid_column")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testFindAllCustomerWithFullParams() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/app/customers?"
            + "page=1"
            + "&itemsPerPage=10"
            + "&sortBy=name"
            + "&sortDesc=false"
            + "&countryName=Uganda"
            + "&validity=false")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value("1"))
        .andExpect(jsonPath("$.content[0].country").value("Uganda"))
        .andExpect(jsonPath("$.content[0].name").value("d_customer"));
  }
}
