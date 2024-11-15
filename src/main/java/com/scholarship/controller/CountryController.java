package com.scholarship.controller;

import com.scholarship.dto.request.CountryRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.CountryResponse;
import com.scholarship.service.CountryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/country")
@AllArgsConstructor
public class CountryController {
    private final CountryService countryService;
    @PostMapping
    public ApiResponse<CountryResponse> createCountry(@RequestBody CountryRequest requests){
      var result =   countryService.createCountry(requests);
      return ApiResponse.<CountryResponse>builder().result(result).build();
    }
//    @PostMapping("/abcd")
//    public ApiResponse<Void> createCountry(@RequestBody CountryRequest[] requests){
//       countryService.createCountries(requests);
//        return ApiResponse.<Void>builder().message("ok").build();
//    }

    @GetMapping
    public ApiResponse<CountryResponse> searchCountries(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1",required = false,value = "page") int page,
            @RequestParam(defaultValue = "10",required = false,value = "limit") int limit,
            @RequestParam(required = false,value = "code") String code) {
        if(code != null){
            var result = countryService.getCountry(code);
            return ApiResponse.<CountryResponse>builder().result(result).build();
        }else {
            Pageable pageable = PageRequest.of(page - 1, limit);
            var result = countryService.searchCountries(keyword, pageable);
            return ApiResponse.<CountryResponse>builder().results(result.getContent())
                    .totalPages(result.getTotalPages())
                    .totalItems((int) result.getTotalElements())
                    .build();
        }
    }
    @GetMapping("/all")
    public ApiResponse<CountryResponse> getAllCountries(){
        var results = countryService.getAllCoutries();
        return ApiResponse.<CountryResponse>builder().results(results).build();
    }
    @DeleteMapping
    public ApiResponse<Void> deleteCountry(@RequestParam(value = "code") String id){
        countryService.deleteCountry(id);
        return ApiResponse.<Void>builder().message("ok").build();
    }
    @PutMapping
    public ApiResponse<CountryResponse> updateCountry(@Valid @RequestBody CountryRequest request){
        var result = countryService.updateCountry(request);
        return  ApiResponse.<CountryResponse>builder().result(result).build();
    }
}
