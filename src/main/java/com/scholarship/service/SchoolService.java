package com.scholarship.service;

import com.scholarship.dto.request.SchoolRequest;
import com.scholarship.dto.response.FieldOfStudyResponse;
import com.scholarship.dto.response.SchoolResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolService {
    SchoolResponse createSchool(SchoolRequest request);
    Page<SchoolResponse> searchSchoolResponsePage(String keyword, Pageable pageable);
    void deleteSchool(int id);
    SchoolResponse updateSchool(SchoolRequest request);
    SchoolResponse getSchool(int id);
    void createSchools(SchoolRequest[] requests);
}
