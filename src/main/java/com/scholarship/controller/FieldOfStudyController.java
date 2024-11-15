package com.scholarship.controller;

import com.scholarship.dto.request.CountryRequest;
import com.scholarship.dto.request.FieldOfStudyRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.CountryResponse;
import com.scholarship.dto.response.FieldOfStudyResponse;
import com.scholarship.service.FieldOfStudyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/field-of-study")
@AllArgsConstructor
public class FieldOfStudyController {
    private final FieldOfStudyService fieldOfStudyService;

    @PostMapping
    public ApiResponse<FieldOfStudyResponse> createFieldOfStudy(@Valid @RequestBody FieldOfStudyRequest request){
      var result =   fieldOfStudyService.creatFieldOfStudy(request);
      return ApiResponse.<FieldOfStudyResponse>builder().result(result).build();
    }
    @GetMapping
    public ApiResponse<FieldOfStudyResponse> searchFieldOfStudies(
            @RequestParam(required = false,value = "keyword") String keyword,
            @RequestParam(defaultValue = "1",required = false,value = "page") int page,
            @RequestParam(defaultValue = "10",required = false,value = "limit") int limit,
             @RequestParam(defaultValue = "-1",required = false,value = "id") int id){
        if(id == -1 ){
            Pageable pageable = PageRequest.of(page-1, limit);
            var result = fieldOfStudyService.searchFieldOfStudyResponsePage(keyword, pageable);
            return ApiResponse.<FieldOfStudyResponse>builder().results(result.getContent())
                    .totalPages(result.getTotalPages())
                    .totalItems((int) result.getTotalElements())
                    .build();
        }else{
            var result = fieldOfStudyService.getFieldOfStudy(id);
            return ApiResponse.<FieldOfStudyResponse>builder().result(result).build();
        }
    }
    @DeleteMapping
    public ApiResponse<Void> deleteFieldOfStudy(@RequestParam(value = "id") int id){
        fieldOfStudyService.deleteFieldOfStudy(id);
        return ApiResponse.<Void>builder().message("ok").build();
    }
    @PutMapping
    public ApiResponse<FieldOfStudyResponse> updateFieldOfStudy(@Valid @RequestBody FieldOfStudyRequest request){
        var result = fieldOfStudyService.updateFieldOfStudy(request);
        return  ApiResponse.<FieldOfStudyResponse>builder().result(result).build();
    }
}
