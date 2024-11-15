package com.scholarship.service.impl;

import com.scholarship.dto.request.CountryRequest;
import com.scholarship.dto.response.CountryResponse;
import com.scholarship.entities.Country;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.CountryMapper;
import com.scholarship.repositories.CountryRepository;
import com.scholarship.service.CountryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Override
    @Transactional
    public CountryResponse createCountry(CountryRequest requests) {
        if (countryRepository.existsByName(requests.getName()) || countryRepository.existsById(requests.getCode())) {
            throw new RuntimeException("Existing Country");
        } else {
            Country countries = countryMapper.toCountry(requests);
            return countryMapper.toCountryResponse(countryRepository.save(countries));
        }
    }

    @Override
    public void createCountries(CountryRequest[] requests) {
        for (CountryRequest countryRequest : requests) {
            Country countries = countryMapper.toCountry(countryRequest);
            countryMapper.toCountryResponse(countryRepository.save(countries));
        }
    }

    public Page<CountryResponse> searchCountries(String keyword, Pageable pageable) {
        return countryRepository.findByKeyword(keyword, pageable).map(countryMapper::toCountryResponse);
    }

    @Override
    public void deleteCountry(String code) {
        boolean exists = countryRepository.existsById(code);
        if (exists) {
            countryRepository.deleteById(code);
        } else {
            throw new AppException(ErrorCode.COUNTRY_NOT_EXISTED);
        }
    }

    @Override
    @Transactional
    public CountryResponse updateCountry(CountryRequest request) {
        Country country = countryRepository.findById(request.getCode()).orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        country.setName(request.getName());
        country.setContinent(request.getContinent());
        countryRepository.save(country);
        return countryMapper.toCountryResponse(country);
    }

    @Override
    public CountryResponse getCountry(String code) {
        Country country = countryRepository.findById(code).orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        return countryMapper.toCountryResponse(country);
    }

    @Override
    public List<CountryResponse> getAllCoutries() {
        List<Country> countryList = countryRepository.findAll();
        List<CountryResponse> countryResponseList = countryList.stream().map(countryMapper::toCountryResponse).toList();
        return countryResponseList;
    }
}
