package com.scholarship.service;

import com.scholarship.dto.request.CountryRequest;
import com.scholarship.dto.response.CountryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    CountryResponse  createCountry(CountryRequest requests);
    void  createCountries(CountryRequest[] requests);
    Page<CountryResponse> searchCountries(String keyword, Pageable pageable);
    void deleteCountry(String code);
    CountryResponse updateCountry(CountryRequest request);
    CountryResponse getCountry(String code);

    List<CountryResponse> getAllCoutries();
}
