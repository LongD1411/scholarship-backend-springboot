package com.scholarship.controller;

import com.scholarship.dto.request.ScholarshipRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.dto.response.SearchKeywordResponse;
import com.scholarship.service.ScholarshipService;
import com.scholarship.service.SearchKeywordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scholarship")
@AllArgsConstructor
public class ScholarshipControlller {
    private final ScholarshipService scholarshipService;
    private final SearchKeywordService searchKeywordService;
    @PostMapping
    public ApiResponse<ScholarshipResponse> create(@Valid @RequestBody ScholarshipRequest request){
        var result = scholarshipService.createScholarship(request);
        return  ApiResponse.<ScholarshipResponse>builder().result(result).build();
    }
    @GetMapping
    public ApiResponse<ScholarshipResponse> searchCountries(
            @RequestParam(required = false,value = "keyword") String keyword,
            @RequestParam(defaultValue = "1",required = false,value = "page") int page,
            @RequestParam(defaultValue = "10",required = false, value = "limit") int limit,
            @RequestParam(defaultValue = "-1",required = false, value = "id") int id) {
      if(id >0){
          var result = scholarshipService.getScholarship(id);
          return ApiResponse.<ScholarshipResponse>builder().result(result).build();
      }else{
          Pageable pageable = PageRequest.of(page-1, limit);
          var result = scholarshipService.searchScholarshipResponsePage(keyword, pageable);
          return ApiResponse.<ScholarshipResponse>builder().results(result.getContent())
                  .totalPages(result.getTotalPages())
                  .totalItems((int) result.getTotalElements())
                  .build();
      }
    }
//    @PostMapping("/all")
//    public ApiResponse<ScholarshipResponse> createScholarships(@Valid @RequestBody ScholarshipRequest[] request){
//        scholarshipService.createScholarships(request);
//        return  ApiResponse.<ScholarshipResponse>builder().message("ok").build();
//    }
    @DeleteMapping
    public ApiResponse<Void> deleteScholarship(@RequestParam(value = "id") int id){
        scholarshipService.deleteScholarship(id);
        return ApiResponse.<Void>builder().message("ok").build();
    }
    @PutMapping
    public ApiResponse<ScholarshipResponse> updateScholarship(@Valid @RequestBody ScholarshipRequest request){
        var result = scholarshipService.updateScholarship(request);
        return ApiResponse.<ScholarshipResponse>builder().result(result).build();
    }
    @GetMapping("/expiring-scholarship")
    public ApiResponse<Long> countExpiringScholarships(){
        return ApiResponse.<Long>builder().result(scholarshipService.expiringScholarships()).build();
    }
    @GetMapping("/scholarships-updated-last-week")
    public  ApiResponse<Long> countScholarshipsUpdatedLastWeek() {
       return ApiResponse.<Long>builder().result(scholarshipService.scholarshipsUpdatedWithinLastWeek()).build();
    }
    @GetMapping("/scholarships-by-month")
    public ApiResponse<Map<String,Long>> countByMonth(){
        return ApiResponse.<Map<String, Long>>builder().result(scholarshipService.getScholarshipsCountByMonth()).build();
    }
    @GetMapping("/scholarships-top-countries")
    public ApiResponse<Object> getTop10CountriesByScholarshipCount() {
        return ApiResponse.<Object>builder().results(scholarshipService.getTop10CountriesByScholarshipCount()).build();
    }
    @GetMapping("/scholarships-top-field-of-study")
    public ApiResponse<Object> getTop5FieldOfStudyByScholarshipCount() {
        return ApiResponse.<Object>builder().results(scholarshipService.getTop5ByScFieldOfStudyScholarshipCount()).build();
    }
    @GetMapping("/top-search")
    public ApiResponse<SearchKeywordResponse> getTop10Search() {
        return ApiResponse.<SearchKeywordResponse>builder().results(searchKeywordService.getTop10SearchKeyword()).build();
    }
}
