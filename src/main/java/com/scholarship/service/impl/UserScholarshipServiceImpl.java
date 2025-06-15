package com.scholarship.service.impl;

import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.entities.Scholarship;
import com.scholarship.entities.User;
import com.scholarship.entities.UserScholarship;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.ScholarshipMapper;
import com.scholarship.repositories.ScholarshipRepository;
import com.scholarship.repositories.UserRepository;
import com.scholarship.repositories.UserScholarshipRepository;
import com.scholarship.service.UserScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserScholarshipServiceImpl implements UserScholarshipService {
    private final UserScholarshipRepository userScholarshipRepository;
    private  final ScholarshipMapper scholarshipMapper;
    private final UserRepository userRepository;
    private final ScholarshipRepository scholarshipRepository;
    @Override
    public List<ScholarshipResponse> getScholarshipsOfUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List scholarships = userScholarshipRepository.findScholarshipsByUserEmail(email);
        return scholarshipMapper.toScholarshipResponses(scholarships);
    }

    @Override
    public void createUserScholarShip(int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED));

        if (userScholarshipRepository.existsByUserAndScholarship(user, scholarship)) {
            throw  new AppException(ErrorCode.SCHOLARSHIP_EXISTED);
        }
        UserScholarship userScholarship = new UserScholarship();
        userScholarship.setUser(user);
        userScholarship.setScholarship(scholarship);
        userScholarshipRepository.save(userScholarship);
    }

    @Override
    public void deleteUserScholarShip(int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED));

        UserScholarship userScholarship = userScholarshipRepository
                .findByUserAndScholarship(user, scholarship)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOLARSHIP_NOT_EXISTED));

        userScholarshipRepository.delete(userScholarship);
    }

}
