package com.skenariolabs.propertyviewer.service;

import com.skenariolabs.propertyviewer.model.repo.Building;
import com.skenariolabs.propertyviewer.model.request.BuildingRequest;
import com.skenariolabs.propertyviewer.model.response.BuildingRepresentation;
import com.skenariolabs.propertyviewer.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private GeoapifyService geoapifyService;

    public Mono<BuildingRepresentation> saveBuilding(BuildingRequest building) {
        Building newBuilding = mapToEntity(building);
        return geoapifyService.fetchCoordinates(newBuilding)
                .flatMap(coordinates -> {
                    newBuilding.setLatitude(coordinates.getLatitude());
                    newBuilding.setLongitude(coordinates.getLongitude());
                    return buildingRepository.save(newBuilding);
                }).map(this::mapToResponse);
    }


    public Mono<Page<BuildingRepresentation>> getAllBuildings(Pageable pageable) {
        return buildingRepository.findAllBy(pageable)
                .collectList()
                .zipWith(this.buildingRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2())).map(this::mapToResponsePage);

    }


    public Flux<BuildingRepresentation> getAllBuildings() {
        return buildingRepository.findAll().map(this::mapToResponse);

    }

    public Mono<BuildingRepresentation> findBuildingById(Long id) {
        return buildingRepository.findById(id).map(this::mapToResponse);
    }

    public Mono<BuildingRepresentation> updateBuilding(Long id, BuildingRequest building) {
        Building newBuilding = mapToEntity(building);
        return buildingRepository.findById(id)
                .flatMap(existingBuilding -> {
                    boolean isAddressChanged = isAddressChanged(existingBuilding, newBuilding);
                    copyEmptyFields(newBuilding, existingBuilding);
                    if (isAddressChanged) {
                        return geoapifyService.fetchCoordinates(newBuilding)
                                .flatMap(coordinates -> {
                                    newBuilding.setLatitude(coordinates.getLatitude());
                                    newBuilding.setLongitude(coordinates.getLongitude());
                                    newBuilding.setId(id);
                                    return buildingRepository.save(newBuilding);
                                });
                    } else {
                        newBuilding.setId(id);
                        return buildingRepository.save(newBuilding);
                    }
                }).map(this::mapToResponse);
    }

    public Mono<Void> addMultipleBuildings(List<BuildingRequest> buildingRequests) {
        return Flux.fromIterable(buildingRequests)
                .flatMap(buildingRequest -> {
                    Building building = this.mapToEntity(buildingRequest);
                    return geoapifyService.fetchCoordinates(building)
                            .map(coordinates -> {
                                building.setLatitude(coordinates.getLatitude());
                                building.setLongitude(coordinates.getLongitude());
                                return building;
                            });
                })
                .collectList()
                .flatMapMany(list ->
                        buildingRepository.saveAll(list)
                ).then();
    }

    public Mono<Void> deleteBuilding(Long id) {
        return buildingRepository.deleteById(id);
    }


    private Page<BuildingRepresentation> mapToResponsePage(Page<Building> buildings) {
        return buildings.map(this::mapToResponse);
    }


    private void copyEmptyFields(Building newBuilding, Building existingBuilding) {
        if (newBuilding.getStreet() == null) {
            newBuilding.setStreet(existingBuilding.getStreet());
        }
        if (newBuilding.getCity() == null) {
            newBuilding.setCity(existingBuilding.getCity());
        }
        if (newBuilding.getPostCode() == null) {
            newBuilding.setPostCode(existingBuilding.getPostCode());
        }
        if (newBuilding.getCountry() == null) {
            newBuilding.setCountry(existingBuilding.getCountry());
        }
        if (newBuilding.getNumber() == null) {
            newBuilding.setNumber(existingBuilding.getNumber());
        }
        if (newBuilding.getLatitude() == null) {
            newBuilding.setLatitude(existingBuilding.getLatitude());
        }
        if (newBuilding.getLongitude() == null) {
            newBuilding.setLongitude(existingBuilding.getLongitude());
        }
    }

    //create a methode to check if address changed in building object
    private boolean isAddressChanged(Building building, Building existingBuilding) {
        return checkIfNotStringsEqual(building.getStreet(), existingBuilding.getStreet()) ||
                checkIfNotStringsEqual(building.getCity(), existingBuilding.getCity()) ||
                checkIfNotStringsEqual(building.getPostCode(), existingBuilding.getPostCode()) ||
                checkIfNotStringsEqual(building.getCountry(), existingBuilding.getCountry()) ||
                checkIfNotStringsEqual(building.getNumber(), existingBuilding.getNumber());

    }

    private boolean checkIfNotStringsEqual(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return !a.equals(b);
    }

    private BuildingRepresentation mapToResponse(Building building) {
        BuildingRepresentation response = new BuildingRepresentation();
        response.setId(building.getId());
        response.setName(building.getName());
        response.setStreet(building.getStreet());
        response.setNumber(building.getNumber());
        response.setPostCode(building.getPostCode());
        response.setCity(building.getCity());
        response.setCountry(building.getCountry());
        response.setDescription(building.getDescription());
        response.setLatitude(building.getLatitude());
        response.setLongitude(building.getLongitude());
        return response;
    }

    private Building mapToEntity(BuildingRequest buildingRequest) {
        Building building = new Building();
        building.setName(buildingRequest.getName());
        building.setStreet(buildingRequest.getStreet());
        building.setNumber(buildingRequest.getNumber());
        building.setPostCode(buildingRequest.getPostCode());
        building.setCity(buildingRequest.getCity());
        building.setCountry(buildingRequest.getCountry());
        building.setDescription(buildingRequest.getDescription());
        return building;
    }

}
