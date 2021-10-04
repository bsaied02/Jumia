package com.springboot.models;

import org.springframework.data.domain.Sort;

/**
 * Model representing the data filter passes to the service
 */
public class CustomerFilter {

  private int pageIndex;
  private int itemsPerPage;
  private Sort customerSort;
  private CustomerFilterType type;
  private String searchQuery;

  public CustomerFilter(int pageIndex, int itemsPerPage) {
    this.pageIndex = pageIndex;
    this.itemsPerPage = itemsPerPage;
  }

  public CustomerFilter(int pageIndex, int offset, Sort customerSort) {
    this(pageIndex, offset);
    this.customerSort = customerSort;
  }

  public int getPageIndex() {
    return pageIndex;
  }

   public int getItemsPerPage() {
    return itemsPerPage;
  }

  public Sort getCustomerSort() {
    return customerSort;
  }

  public CustomerFilterType getType() {
    return type;
  }

  public void setType(CustomerFilterType type) {
    this.type = type;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }
}
