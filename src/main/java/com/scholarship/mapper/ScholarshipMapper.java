package com.scholarship.mapper;

import com.scholarship.dto.request.ScholarshipRequest;
import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.entities.Scholarship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScholarshipMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "school", ignore = true)
    @Mapping(target = "fieldOfStudy", ignore = true)
    Scholarship toScholarship(ScholarshipRequest request);

    @Mapping(target = "schoolName", source = "school.name")
    @Mapping(target = "fieldOfStudyName",source = "fieldOfStudy.name")
    @Mapping(target = "schoolId",source = "school.id")
    @Mapping(target = "fieldOfStudyId",source = "fieldOfStudy.id")
    @Mapping(target = "countryName",source = "school.country.name")
    @Mapping(target = "schoolRank", source = "school.rankValue")
    ScholarshipResponse toScholarshipResponse(Scholarship scholarship);


    List<ScholarshipResponse> toScholarshipResponses(List<Scholarship> scholarships);
}
