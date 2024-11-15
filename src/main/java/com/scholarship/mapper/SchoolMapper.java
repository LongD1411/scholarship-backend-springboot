package com.scholarship.mapper;

import com.scholarship.dto.request.SchoolRequest;
import com.scholarship.dto.response.SchoolResponse;
import com.scholarship.entities.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ScholarshipMapper.class})
public interface SchoolMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "scholarships", ignore = true)
    School toSchool(SchoolRequest request);

    @Mapping(target = "countryName",source = "country.name")
    @Mapping(target = "countryCode", source = "country.code")
    SchoolResponse toSchoolResponse(School request);

    School newSchool(School oldSchool);
}
