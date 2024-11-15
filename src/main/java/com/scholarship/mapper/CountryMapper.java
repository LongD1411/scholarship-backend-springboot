package com.scholarship.mapper;

import com.scholarship.dto.request.CountryRequest;
import com.scholarship.dto.response.CountryResponse;
import com.scholarship.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {SchoolMapper.class})
public interface CountryMapper {
     Country toCountry(CountryRequest request);
     CountryResponse toCountryResponse(Country entity);
     List<Country> toCountries(CountryRequest[] requests);
    List<CountryResponse> toCountryResponses(List<Country> requests);
}
