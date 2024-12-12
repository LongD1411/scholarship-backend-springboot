package com.scholarship.repositories;

import com.scholarship.entities.Country;
import com.scholarship.entities.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScholarshipRepository  extends JpaRepository<Scholarship,Integer> {
    @Query("SELECT s FROM Scholarship s " +
            "WHERE (:keyword IS NULL OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(CAST(s.gpa AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.school.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:fosId IS NULL OR s.fieldOfStudy.id = :fosId)" +
            "AND (:countryCode IS NULL OR LOWER(s.school.country.code) LIKE LOWER(CONCAT('%', :countryCode, '%')))")
    Page<Scholarship> findByKeyword(@Param("keyword") String keyword,
                                    @Param("countryCode") String countryCode,
                                    @Param("fosId") String fosId ,
                                    Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM scholarship WHERE end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 2 WEEK)", nativeQuery = true)
    long countExpiringScholarships();
    @Query("SELECT COUNT(s) FROM Scholarship s WHERE s.updatedAt > :oneWeekAgo")
    long countScholarshipsUpdatedWithinLastWeek(LocalDateTime oneWeekAgo);

    @Query("SELECT MONTH(s.startDate) AS month, YEAR(s.startDate) AS year, COUNT(s) AS total " +
            "FROM Scholarship s " +
            "WHERE YEAR(s.startDate) = 2024 " +
            "GROUP BY YEAR(s.startDate), MONTH(s.startDate) " +
            "ORDER BY month")
    List<Object[]> countScholarshipsByMonth();

    @Query("SELECT s.school.country, COUNT(s) as scholarshipCount " +
            "FROM Scholarship s " +
            "GROUP BY s.school.country " +
            "ORDER BY scholarshipCount DESC " +
            "LIMIT 10")
    List<Object> findTop10CountriesByScholarshipCount();
    @Query("SELECT f.name, COUNT(s) AS scholarshipCount " +
            "FROM Scholarship s " +
            "JOIN s.fieldOfStudy f " +
            "GROUP BY f.name " +
            "ORDER BY scholarshipCount DESC " +
            "LIMIT 5")
    List<Object> findTop5FieldOfStudyByScholarshipCount();

}

