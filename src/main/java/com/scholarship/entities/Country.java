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
public class Country {
    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    private String name;

    private String continent;

    @OneToMany(mappedBy = "country",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<School> schools = new ArrayList<>();;

    @PreRemove
    private void preRemove() {
        for (School school : schools) {
            school.setCountry(null);  // Set country về null thay vì xóa School
        }
    }
}
