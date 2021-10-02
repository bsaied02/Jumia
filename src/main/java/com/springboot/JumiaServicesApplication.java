package com.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JumiaServicesApplication {

  private static final Logger log = LoggerFactory.getLogger(JumiaServicesApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(JumiaServicesApplication.class, args);
  }

}
