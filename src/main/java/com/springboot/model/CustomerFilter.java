package com.springboot.model;

import org.springframework.data.domain.Sort;

public class CustomerFilter {

  private int pageIndex;
  private int offset;
  private Sort customerSort;
  private CustomerFilterType type;
  private String searchQuery;

  public CustomerFilter(int pageIndex, int offset) {
    this.pageIndex = pageIndex;
    this.offset = offset;
  }

  public CustomerFilter(int pageIndex, int offset, Sort customerSort) {
    this(pageIndex, offset);
    this.customerSort = customerSort;
  }

  public int getPageIndex() {
    return pageIndex;
  }

   public int getOffset() {
    return offset;
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
