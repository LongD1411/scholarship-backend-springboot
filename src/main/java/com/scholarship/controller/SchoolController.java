package com.scholarship.controller;

import com.scholarship.dto.request.SchoolRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.SchoolResponse;
import com.scholarship.service.SchoolService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ApiResponse<SchoolResponse> createSchool(@Valid @RequestBody SchoolRequest request) {
        var result = schoolService.createSchool(request);
        return ApiResponse.<SchoolResponse>builder().result(result).build();
    }
//    @PostMapping("/abcd")
//    public ApiResponse<Void> createSchool(@Valid @RequestBody SchoolRequest[] requests) {
//        schoolService.createSchools(requests);
//        return ApiResponse.<Void>builder().message("Ok").build();
//    }
    @GetMapping
    public ApiResponse<SchoolResponse> searchSchools(
            @RequestParam(required = false, value = "keyword") String keyword,
            @RequestParam(defaultValue = "1", required = false, value = "page") int page,
            @RequestParam(defaultValue = "10", required = false, value = "limit") int limit,
            @RequestParam(defaultValue = "-1",required = false, value = "id") int id,
            @RequestParam(required = false, value = "countryCode") String countryCode){
        if(id > 0){
            var result = schoolService.getSchool(id);
            return ApiResponse.<SchoolResponse>builder().result(result).build();
        }else{
            if (limit > 20) {
                return ApiResponse.<SchoolResponse>builder().build();
            }
            Pageable pageable = PageRequest.of(page - 1, limit);
            var result = schoolService.searchSchoolResponsePage(keyword,countryCode, pageable);
            return ApiResponse.<SchoolResponse>builder().results(result.getContent())
                    .totalPages(result.getTotalPages())
                    .totalItems((int) result.getTotalElements())
                    .build();
        }
    }
    @DeleteMapping
    public ApiResponse<Void> deleteSchool(@RequestParam(value = "id") int id) {
        schoolService.deleteSchool(id);
        return ApiResponse.<Void>builder().message("ok").build();
    }

    @PutMapping
    public ApiResponse<SchoolResponse> updateSchool(@Valid @RequestBody SchoolRequest request) {
        var result = schoolService.updateSchool(request);
        return ApiResponse.<SchoolResponse>builder().result(result).build();
    }
}
