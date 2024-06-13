package com.skenariolabs.propertyviewer.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRepresentation {
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
