package com.springboot.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.models.CountryProperties;

@Component
public class PhoneValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneValidator.class);
  private final static List<CountryProperties> countryPropertiesList = new ArrayList<>();

  public PhoneValidator() {
    init();
  }

  private void init() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      URL resource = ClassLoader.getSystemResource("valid-countries-regex.json");
      File file = Paths.get(resource.toURI()).toFile();
      List<CountryProperties> countries = mapper.readValue(file, new TypeReference<List<CountryProperties>>() {
      });
      countries.stream()
          .forEach(countryProperty -> countryPropertiesList.add(countryProperty));
    } catch (IOException | URISyntaxException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public static boolean isValidPhone(@NotNull String phone, @NotNull String countryRegex) {
    return Pattern.compile((countryRegex)).matcher(phone).matches();
  }

  public static Optional<CountryProperties> getCountryByPhone(@NotNull String phone) {
    return countryPropertiesList.stream()
        .filter(countryProperty -> {
          String phoneCode = StringUtils.substringBetween(phone, "(", ")");
          String countryCode = countryProperty.getCountryCode().replaceAll("[^0-9]", "");
          return Objects.equals(countryCode, phoneCode);
        })
        .findFirst();
  }

  public static Optional<CountryProperties> getCountryByCountryName(@NotNull String countryName) {
    return countryPropertiesList.stream()
        .filter(countryProperty -> Objects.equals(countryProperty.getCountry(), countryName))
        .findFirst();
  }

  public static List<String> getAllCountriesNames() {
    return countryPropertiesList.stream()
        .map(CountryProperties::getCountry)
        .collect(Collectors.toList());
  }

  public static List<String> getAllCountriesRegex() {
    return countryPropertiesList.stream()
        .map(CountryProperties::getRegex)
        .collect(Collectors.toList());
  }
}
