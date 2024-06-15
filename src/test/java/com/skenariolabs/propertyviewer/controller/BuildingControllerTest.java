package com.skenariolabs.propertyviewer.controller;

import com.skenariolabs.propertyviewer.model.request.BuildingForm;
import com.skenariolabs.propertyviewer.model.request.BuildingRequest;
import com.skenariolabs.propertyviewer.model.response.BuildingRepresentation;
import com.skenariolabs.propertyviewer.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetAllBuildings() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")));
        Page<BuildingRepresentation> buildingPage = new PageImpl<>(List.of(new BuildingRepresentation()), pageable, 1);
        when(buildingService.getAllBuildings(pageable)).thenReturn(Mono.just(buildingPage));

        MvcResult mvcResult = mockMvc.perform(get("/buildings")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andReturn();


        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("buildings"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("sortProperty", "id"))
                .andExpect(model().attribute("sortDirection", "asc"));
    }

    @Test
    public void testAddMultipleBuildings() throws Exception {
        BuildingForm buildingForm = new BuildingForm();
        when(buildingService.addMultipleBuildings(any())).thenReturn(Mono.empty());
        MvcResult mvcResult = mockMvc.perform(post("/buildings/add-multiple")
                        .flashAttr("buildingForm", buildingForm))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/buildings"));
    }

    @Test
    public void testAddBuildingForm() throws Exception {
        mockMvc.perform(get("/buildings/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"));
    }

    @Test
    public void testCreateBuilding() throws Exception {
        BuildingRequest buildingRequest = new BuildingRequest();
        when(buildingService.saveBuilding(any())).thenReturn(Mono.empty());
        MvcResult mvcResult = mockMvc.perform(post("/buildings")
                        .flashAttr("buildingRequest", buildingRequest))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/buildings"));
    }

    @Test
    public void testEditBuildingForm() throws Exception {
        Long id = 1L;
        BuildingRepresentation building = new BuildingRepresentation();
        when(buildingService.findBuildingById(id)).thenReturn(Mono.just(building));
        MvcResult mvcResult = mockMvc.perform(get("/buildings/edit/{id}", id))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("building"));
    }

    @Test
    public void testUpdateBuilding() throws Exception {
        Long id = 1L;
        BuildingRequest buildingRequest = new BuildingRequest();
        when(buildingService.updateBuilding(id, buildingRequest)).thenReturn(Mono.empty());
        MvcResult mvcResult = mockMvc.perform(post("/buildings/{id}", id)
                        .flashAttr("buildingRequest", buildingRequest))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/buildings"));
    }

    @Test
    public void testDeleteBuilding() throws Exception {
        Long id = 1L;
        when(buildingService.deleteBuilding(id)).thenReturn(Mono.empty());
        MvcResult mvcResult = mockMvc.perform(get("/buildings/delete/{id}", id))
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/buildings"));
    }
}

