package com.scholarship.service.impl;

import com.scholarship.dto.request.FieldOfStudyRequest;
import com.scholarship.dto.response.FieldOfStudyResponse;
import com.scholarship.entities.FieldOfStudy;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.FieldOfStudyMapper;
import com.scholarship.mapper.ScholarshipMapper;
import com.scholarship.repositories.FieldOfStudyRepository;
import com.scholarship.service.FieldOfStudyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FieldOfStudyServiceImpl implements FieldOfStudyService {
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final FieldOfStudyMapper fieldOfStudyMapper;
    private final ScholarshipMapper scholarshipMapper;
    @Override
    @Transactional
    public FieldOfStudyResponse creatFieldOfStudy(FieldOfStudyRequest request) {
        if(fieldOfStudyRepository.existsByName(request.getName())){
            throw  new RuntimeException("Existing Field Of Study");
        }
        FieldOfStudy fieldOfStudy =FieldOfStudy.builder().name(request.getName()).build();
        fieldOfStudyRepository.save(fieldOfStudy);
        return FieldOfStudyResponse.builder().name(fieldOfStudy.getName())
                .id(fieldOfStudy.getId())
                .build();
    }

    @Override
    public Page<FieldOfStudyResponse> searchFieldOfStudyResponsePage(String keyword, Pageable pageable) {
        return fieldOfStudyRepository.findByKeyword(keyword, pageable).map(fieldOfStudy -> FieldOfStudyResponse.builder()
                .name(fieldOfStudy.getName())
                .id(fieldOfStudy.getId())
                .scholarships(scholarshipMapper.toScholarshipResponses(fieldOfStudy.getScholarships()))
                .build());
    }

    @Override
    @Transactional
    public void deleteFieldOfStudy(int id) {
        boolean exists = fieldOfStudyRepository.existsById(id);
        if(exists){
            fieldOfStudyRepository.deleteById(id);
        }else{
            throw new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED);
        }
    }

    @Override
    public FieldOfStudyResponse getFieldOfStudy(int id) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED));
        return fieldOfStudyMapper.toFieldOfStudyResponse(fieldOfStudy);
    }

    @Override
    public FieldOfStudyResponse updateFieldOfStudy(FieldOfStudyRequest request) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(request.getId()).orElseThrow(()-> new AppException(ErrorCode.FIELD_OF_STUDY_NOT_EXISTED));
        fieldOfStudy.setName(request.getName());
        fieldOfStudyRepository.save(fieldOfStudy);
        return fieldOfStudyMapper.toFieldOfStudyResponse(fieldOfStudy);
    }
}
