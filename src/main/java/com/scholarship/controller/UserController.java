package com.scholarship.controller;

import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.ScholarshipResponse;
import com.scholarship.entities.Scholarship;
import com.scholarship.service.UserScholarshipService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserScholarshipService userScholarshipService;
    @GetMapping("/saved-scholarships")
    public ApiResponse<ScholarshipResponse> getUserSavedScholarships() {
        List<ScholarshipResponse> scholarships = userScholarshipService.getScholarshipsOfUser();
        return ApiResponse.<ScholarshipResponse>builder()
                .results(scholarships)
                .build();
    }
    @PostMapping("/save-scholarship")
    public ApiResponse<Void> saveScholarship(@RequestParam(value = "id") int id) {
        userScholarshipService.createUserScholarShip(id);
        return ApiResponse.<Void>builder()
                .message("Lưu thành công")
                .build();
    }
    @DeleteMapping("/unsave-scholarship")
    public ApiResponse<Void> unSaveScholarship(@RequestParam(value = "id") int id) {
        userScholarshipService.deleteUserScholarShip(id);
        return ApiResponse.<Void>builder()
                .message("Lưu thành công")
                .build();
    }
}
