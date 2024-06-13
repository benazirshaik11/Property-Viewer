package com.skenariolabs.propertyviewer.controller;

import com.skenariolabs.propertyviewer.model.request.BuildingForm;
import com.skenariolabs.propertyviewer.model.request.BuildingRequest;
import com.skenariolabs.propertyviewer.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public Mono<String> getAllBuildings(Model model,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort.Order order = new Sort.Order(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return buildingService.getAllBuildings(pageable)
                .doOnNext(buildingPage -> {
                    model.addAttribute("buildings", buildingPage.getContent());
                    model.addAttribute("page", buildingPage);
                    model.addAttribute("sortProperty", sort[0]);
                    model.addAttribute("sortDirection", sort[1]);
                })
                .then(Mono.just("home"));
    }

    @PostMapping("/add-multiple")
    public Mono<String> addMultipleBuildings(@ModelAttribute BuildingForm buildingForm, Model model) {
        return buildingService.addMultipleBuildings(buildingForm.getBuildings())
                .then(Mono.just("redirect:/buildings"));
    }

    @GetMapping("/add")
    public String addBuildingForm() {
        return "add";
    }

    @PostMapping
    public Mono<String> createBuilding(@ModelAttribute BuildingRequest buildingRequest) {
        return buildingService.saveBuilding(buildingRequest)
                .thenReturn("redirect:/buildings");
    }

    @GetMapping("/edit/{id}")
    public Mono<String> editBuildingForm(@PathVariable Long id, Model model) {
        return buildingService.findBuildingById(id)
                .doOnNext(building -> model.addAttribute("building", building))
                .thenReturn("edit");
    }

    @PostMapping("/{id}")
    public Mono<String> updateBuilding(@PathVariable Long id, @ModelAttribute BuildingRequest buildingRequest) {
        return buildingService.updateBuilding(id, buildingRequest)
                .thenReturn("redirect:/buildings");
    }

    @GetMapping("/delete/{id}")
    public Mono<String> deleteBuilding(@PathVariable Long id) {
        return buildingService.deleteBuilding(id)
                .thenReturn("redirect:/buildings");
    }
}
