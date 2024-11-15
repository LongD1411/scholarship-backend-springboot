package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "fieldOfStudy", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Scholarship> scholarships = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        for (Scholarship scholarship : scholarships) {
            scholarship.setFieldOfStudy(null);
        }
    }
}
