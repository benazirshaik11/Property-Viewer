package com.skenariolabs.propertyviewer.controller;

import com.skenariolabs.propertyviewer.model.request.BuildingForm;
import com.skenariolabs.propertyviewer.model.request.BuildingRequest;
import com.skenariolabs.propertyviewer.model.response.BuildingRepresentation;
import com.skenariolabs.propertyviewer.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/api/buildings")
public class BuildingRestController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public Flux<BuildingRepresentation> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @PostMapping("/add-multiple")
    public Mono<Void> addMultipleBuildings(@RequestBody BuildingForm buildingForm) {
        return buildingService.addMultipleBuildings(buildingForm.getBuildings());
    }

    @PostMapping
    public Mono<BuildingRepresentation> createBuilding(@RequestBody BuildingRequest buildingRequest) {
        return buildingService.saveBuilding(buildingRequest);
    }

    @GetMapping("/{id}")
    public Mono<BuildingRepresentation> getBuildingById(@PathVariable Long id) {
        return buildingService.findBuildingById(id);
    }

    @PutMapping("/{id}")
    public Mono<Void> updateBuilding(@PathVariable Long id, @RequestBody BuildingRequest buildingRequest) {
        return buildingService.updateBuilding(id, buildingRequest).then();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBuilding(@PathVariable Long id) {
        return buildingService.deleteBuilding(id).then();
    }

}
