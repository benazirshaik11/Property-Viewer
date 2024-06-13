package com.skenariolabs.propertyviewer.model.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {
    @Id
    private Long id;

    private String name;
    private String street;
    private String number;
    private String postCode;
    private String city;
    private String country;
    private String description;
    private Double latitude;
    private Double longitude;
}
