package com.scholarship.service;

import com.scholarship.dto.request.ScholarshipRequest;
import com.scholarship.dto.response.ScholarshipResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ScholarshipService {
    ScholarshipResponse createScholarship(ScholarshipRequest request);
    Page<ScholarshipResponse> searchScholarshipResponsePage(String keyword,String countryCode,String fosId, Pageable pageable);
    void deleteScholarship(int id);
    ScholarshipResponse updateScholarship(ScholarshipRequest request);
    void createScholarships(ScholarshipRequest[] request);
    ScholarshipResponse getScholarship(int id);
    long expiringScholarships();
    long scholarshipsUpdatedWithinLastWeek();
    public Map<String, Long> getScholarshipsCountByMonth();
    List<Object> getTop10CountriesByScholarshipCount();
    List<Object> getTop5ByScFieldOfStudyScholarshipCount();
}
