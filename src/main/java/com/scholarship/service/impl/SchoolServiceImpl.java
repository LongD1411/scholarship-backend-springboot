package com.scholarship.service.impl;

import com.scholarship.dto.request.SchoolRequest;
import com.scholarship.dto.response.FieldOfStudyResponse;
import com.scholarship.dto.response.SchoolResponse;
import com.scholarship.entities.Country;
import com.scholarship.entities.School;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.SchoolMapper;
import com.scholarship.repositories.CountryRepository;
import com.scholarship.repositories.SchoolRepository;
import com.scholarship.service.SchoolService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final CountryRepository countryRepository;
    private final SchoolMapper schoolMapper;

    @Override
    public SchoolResponse createSchool(SchoolRequest request) {
        Country country = countryRepository.findById(request.getCountryCode()).orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        School school = schoolMapper.toSchool(request);
        school.setCountry(country);
        return schoolMapper.toSchoolResponse(schoolRepository.save(school));
    }

    @Override
    public Page<SchoolResponse> searchSchoolResponsePage(String keyword, Pageable pageable) {
        return schoolRepository.findByKeyword(keyword, pageable).map(schoolMapper::toSchoolResponse);
    }

    @Override
    public void deleteSchool(int id) {
        boolean exists = schoolRepository.existsById(id);
        if (exists) {
            schoolRepository.deleteById(id);
        } else {
            throw new AppException(ErrorCode.SCHOOL_NOT_EXISTED);
        }
    }

    @Override
    @Transactional
    public SchoolResponse updateSchool(SchoolRequest request) {
        Country country = countryRepository.findById(request.getCountryCode()).orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
        School oldSchool = schoolRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        School newSchool = schoolMapper.toSchool(request);
        newSchool.setId(oldSchool.getId());
        newSchool.setCountry(country);
        newSchool.setScholarships(oldSchool.getScholarships());
        schoolRepository.save(newSchool);
        return schoolMapper.toSchoolResponse(newSchool);
    }

    @Override
    public SchoolResponse getSchool(int id) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        return schoolMapper.toSchoolResponse(school);
    }

    @Override
    @Transactional
    public void createSchools(SchoolRequest[] requests) {
        for(SchoolRequest schoolRequest:requests){
            if(schoolRepository.existsByName(schoolRequest.getName())){
                continue;
            };
            School school = schoolMapper.toSchool(schoolRequest);
            Optional<Country> country = countryRepository.findById(schoolRequest.getCountryCode());
            if(country.isPresent()){
                school.setCountry(country.get());
            }
            schoolRepository.save(school);
        }
    }
}
