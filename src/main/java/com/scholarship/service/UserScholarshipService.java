package com.scholarship.service;

import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.entities.Scholarship;

import java.util.List;

public interface UserScholarshipService {
    List<ScholarshipResponse> getScholarshipsOfUser();
    void createUserScholarShip(int id);
    void deleteUserScholarShip(int id);
}
