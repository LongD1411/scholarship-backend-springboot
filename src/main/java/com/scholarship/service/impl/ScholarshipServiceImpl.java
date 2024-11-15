package com.scholarship.service.impl;

import com.scholarship.dto.request.ScholarshipRequest;
import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.dto.response.SchoolResponse;
import com.scholarship.entities.Country;
import com.scholarship.entities.FieldOfStudy;
import com.scholarship.entities.Scholarship;
import com.scholarship.entities.School;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.ScholarshipMapper;
import com.scholarship.repositories.FieldOfStudyRepository;
import com.scholarship.repositories.ScholarshipRepository;
import com.scholarship.repositories.SchoolRepository;
import com.scholarship.service.ScholarshipService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ScholarshipServiceImpl implements ScholarshipService {
    private final ScholarshipRepository scholarshipRepository;
    private final SchoolRepository schoolRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final ScholarshipMapper scholarshipMapper;

    @Override
    @Transactional
    public ScholarshipResponse createScholarship(ScholarshipRequest request) {
        School school = schoolRepository.findById(request.getSchoolId()).orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(request.getFieldOfStudyId()).orElseThrow(() -> new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED));
        Scholarship scholarship = scholarshipMapper.toScholarship(request);
        scholarship.setSchool(school);
        scholarship.setFieldOfStudy(fieldOfStudy);
        scholarshipRepository.save(scholarship);
        return scholarshipMapper.toScholarshipResponse(scholarship);
    }

    @Override
    public Page<ScholarshipResponse> searchScholarshipResponsePage(String keyword, Pageable pageable) {
        return scholarshipRepository.findByKeyword(keyword, pageable).map(scholarshipMapper::toScholarshipResponse);
    }

    @Override
    public void deleteScholarship(int id) {
        if (!scholarshipRepository.existsById(id)) throw new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED);
        scholarshipRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ScholarshipResponse updateScholarship(ScholarshipRequest request) {
        Scholarship oldScholarship = scholarshipRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED));
        School newSchool = schoolRepository.findById(request.getSchoolId()).orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        FieldOfStudy newFieldOfStudy = fieldOfStudyRepository.findById(request.getFieldOfStudyId()).orElseThrow(() -> new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED));
        Scholarship newScholarship = scholarshipMapper.toScholarship(request);
        newScholarship.setId(request.getId());
        newScholarship.setSchool(newSchool);
        newScholarship.setCreatedAt(oldScholarship.getCreatedAt());
        newScholarship.setUpdatedAt(LocalDateTime.now());
        newScholarship.setFieldOfStudy(newFieldOfStudy);
        scholarshipRepository.save(newScholarship);
        return scholarshipMapper.toScholarshipResponse(newScholarship);
    }

    @Override
    public void createScholarships(ScholarshipRequest[] request) {
        for(ScholarshipRequest scholarshipRequest: request){
            Optional<School> school = schoolRepository.findById(scholarshipRequest.getSchoolId());
            School newSchool = new School();
            if(school.isPresent()){
                newSchool = school.get();
            }else {
                 newSchool = schoolRepository.findById(256).orElseThrow(()->new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
                 log.info("Id loi: " + scholarshipRequest.getSchoolId());
            }
            FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(scholarshipRequest.getFieldOfStudyId()).orElseThrow(() -> new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED));
            Scholarship scholarship = scholarshipMapper.toScholarship(scholarshipRequest);
            scholarship.setSchool(newSchool);
            scholarship.setFieldOfStudy(fieldOfStudy);
            scholarshipRepository.save(scholarship);
        }
    }

    @Override
    public ScholarshipResponse getScholarship(int id) {
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED));
        return  scholarshipMapper.toScholarshipResponse(scholarship);
    }

    @Override
    public long expiringScholarships() {
        return scholarshipRepository.countExpiringScholarships();
    }

    @Override
    public long scholarshipsUpdatedWithinLastWeek() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
        return scholarshipRepository.countScholarshipsUpdatedWithinLastWeek(oneWeekAgo);
    }

    @Override
    public Map<String, Long> getScholarshipsCountByMonth() {
        List<Object[]> results = scholarshipRepository.countScholarshipsByMonth();
        Map<String, Long> scholarshipsByMonth = new HashMap<>();
        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Integer year = (Integer) result[1];
            Long total = (Long) result[2];
            String monthYear = year + "-" + month;
            scholarshipsByMonth.put(monthYear, total);
        }
        return scholarshipsByMonth;
    }

    @Override
    public List<Object> getTop10CountriesByScholarshipCount() {
        return scholarshipRepository.findTop10CountriesByScholarshipCount();
    }

    @Override
    public List<Object> getTop5ByScFieldOfStudyScholarshipCount() {
        return scholarshipRepository.findTop5FieldOfStudyByScholarshipCount();
    }
}
