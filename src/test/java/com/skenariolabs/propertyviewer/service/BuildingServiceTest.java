package com.skenariolabs.propertyviewer.service;

import com.skenariolabs.propertyviewer.model.repo.Building;
import com.skenariolabs.propertyviewer.model.request.BuildingRequest;
import com.skenariolabs.propertyviewer.model.response.BuildingRepresentation;
import com.skenariolabs.propertyviewer.model.response.GeoapifyResponse;
import com.skenariolabs.propertyviewer.repository.BuildingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private GeoapifyService geoapifyService;

    @Mock
    private GeoapifyResponse.Feature.Geometry geometry;

    @InjectMocks
    private BuildingService buildingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBuilding() {
        BuildingRequest buildingRequest = new BuildingRequest();
        buildingRequest.setName("Test Building");
        Building building = new Building();
        building.setName("Test Building");


        when(geometry.getLatitude())
                .thenReturn(-1.38373);
        when(geometry.getLongitude())
                .thenReturn(-1.38373);

        when(geoapifyService.fetchCoordinates(any(Building.class)))
                .thenReturn(Mono.just(geometry));
        when(buildingRepository.save(any(Building.class)))
                .thenReturn(Mono.just(building));

        Mono<BuildingRepresentation> result = buildingService.saveBuilding(buildingRequest);

        StepVerifier.create(result)
                .expectNextMatches(buildingRepresentation -> "Test Building".equals(buildingRepresentation.getName()))
                .verifyComplete();
    }

    @Test
    void testGetAllBuildings() {
        Building building = new Building();
        building.setName("Test Building");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        when(buildingRepository.findAllBy(pageable))
                .thenReturn(Flux.just(building));
        when(buildingRepository.count())
                .thenReturn(Mono.just(1L));

        Mono<Page<BuildingRepresentation>> result = buildingService.getAllBuildings(pageable);

        StepVerifier.create(result)
                .expectNextMatches(buildingRepresentationPage ->
                        buildingRepresentationPage.getContent().stream()
                                .anyMatch(buildingRepresentation -> "Test Building".equals(buildingRepresentation.getName())))
                .verifyComplete();
    }

    @Test
    void testFindBuildingById() {
        Long id = 1L;
        Building building = new Building();
        building.setId(id);
        building.setName("Test Building");

        when(buildingRepository.findById(id))
                .thenReturn(Mono.just(building));

        Mono<BuildingRepresentation> result = buildingService.findBuildingById(id);

        StepVerifier.create(result)
                .expectNextMatches(buildingRepresentation -> "Test Building".equals(buildingRepresentation.getName()))
                .verifyComplete();
    }

    @Test
    void testUpdateBuilding() {
        Long id = 1L;
        BuildingRequest buildingRequest = new BuildingRequest();
        buildingRequest.setName("Updated Building");

        Building existingBuilding = new Building();
        existingBuilding.setId(id);
        existingBuilding.setName("Existing Building");

        Building updatedBuilding = new Building();
        updatedBuilding.setId(id);
        updatedBuilding.setName("Updated Building");

        when(buildingRepository.findById(id))
                .thenReturn(Mono.just(existingBuilding));
        when(buildingRepository.save(any(Building.class)))
                .thenReturn(Mono.just(updatedBuilding));

        Mono<BuildingRepresentation> result = buildingService.updateBuilding(id, buildingRequest);

        StepVerifier.create(result)
                .expectNextMatches(buildingRepresentation -> "Updated Building".equals(buildingRepresentation.getName()))
                .verifyComplete();
    }

    @Test
    void testDeleteBuilding() {
        Long id = 1L;

        when(buildingRepository.deleteById(id))
                .thenReturn(Mono.empty());

        Mono<Void> result = buildingService.deleteBuilding(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testAddMultipleBuildings() {
        List<BuildingRequest> buildingRequests = Arrays.asList(new BuildingRequest(), new BuildingRequest());


        Assertions.assertDoesNotThrow(() -> buildingService.addMultipleBuildings(buildingRequests));
    }
}
