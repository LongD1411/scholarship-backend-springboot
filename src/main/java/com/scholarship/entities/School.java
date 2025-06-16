package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String logo;
    private String provider;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int rankValue;
    private int students;
    @Column(name = "field_of_study")
    private Integer fieldOfStudy;
    private String type;
    private String url;
    @Column(name = "top_reason",columnDefinition = "TEXT")
    private String topReason;
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "school", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private List < Scholarship > scholarships = new ArrayList < > ();
    @PreRemove
    private void preRemove() {
        for (Scholarship scholarship: scholarships) {
            scholarship.setSchool(null);
        }
    }
}
