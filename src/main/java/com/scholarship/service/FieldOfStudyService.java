package com.scholarship.service;

import com.scholarship.dto.request.FieldOfStudyRequest;
import com.scholarship.dto.response.CountryResponse;
import com.scholarship.dto.response.FieldOfStudyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FieldOfStudyService {
    FieldOfStudyResponse creatFieldOfStudy(FieldOfStudyRequest request);
    Page<FieldOfStudyResponse> searchFieldOfStudyResponsePage(String keyword, Pageable pageable);
    void deleteFieldOfStudy(int id);
    FieldOfStudyResponse getFieldOfStudy(int id);
    FieldOfStudyResponse updateFieldOfStudy(FieldOfStudyRequest request);
}
