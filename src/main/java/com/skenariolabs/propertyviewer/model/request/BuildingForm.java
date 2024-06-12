package com.skenariolabs.propertyviewer.model.request;

import lombok.Data;

import java.util.List;

@Data
public class BuildingForm {
    private List<BuildingRequest> buildings;
}
