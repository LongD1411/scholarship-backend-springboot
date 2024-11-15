package com.scholarship.mapper;

import com.scholarship.dto.response.FieldOfStudyResponse;
import com.scholarship.entities.FieldOfStudy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ScholarshipMapper.class})
public interface FieldOfStudyMapper {

    FieldOfStudyResponse toFieldOfStudyResponse(FieldOfStudy fieldOfStudy);
}
