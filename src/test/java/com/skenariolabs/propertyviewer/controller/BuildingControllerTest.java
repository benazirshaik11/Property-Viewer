//package com.skenariolabs.propertyviewer.controller;
//
//import com.skenariolabs.propertyviewer.model.Building;
//import com.skenariolabs.propertyviewer.service.BuildingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@WebFluxTest
//public class BuildingControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private BuildingService buildingService;
//
//    private Building building;
//
//    @BeforeEach
//    void setUp() {
//        building = new Building();
//        building.setId(1L);
//        building.setName("Test Building");
//    }
//
//    @Test
//    void testAddBuilding() {
//        when(buildingService.saveBuilding(any(Building.class))).thenReturn(Mono.just(building));
//
//        webTestClient.post()
//                .uri("/api/buildings")
//                .bodyValue(building)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Building.class)
//                .isEqualTo(building);
//    }
//
//    @Test
//    void testGetAllBuildings() {
//        when(buildingService.findAllBuildings()).thenReturn(Flux.just(building));
//
//        webTestClient.get()
//                .uri("/api/buildings")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Building.class)
//                .hasSize(1)
//                .contains(building);
//    }
//
//    @Test
//    void testGetBuildingById() {
//        when(buildingService.findBuildingById(1L)).thenReturn(Mono.just(building));
//
//        webTestClient.get()
//                .uri("/api/buildings/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Building.class)
//                .isEqualTo(building);
//    }
//
//    @Test
//    void testUpdateBuilding() {
//        when(buildingService.updateBuilding(any(Building.class))).thenReturn(Mono.just(building));
//
//        webTestClient.put()
//                .uri("/api/buildings/1")
//                .bodyValue(building)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Building.class)
//                .isEqualTo(building);
//    }
//
//    @Test
//    void testDeleteBuilding() {
//        when(buildingService.deleteBuilding(1L)).thenReturn(Mono.empty());
//
//        webTestClient.delete()
//                .uri("/api/buildings/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Void.class);
//    }
//}
