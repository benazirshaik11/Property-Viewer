package com.skenariolabs.propertyviewer.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ConfigTests {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private WebClient.Builder webClientBuilder;

    //MVC configuration test

    @Test
    public void testLoginView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    //WebClient configuration test

    @Test
    public void testWebClientBuilderNotNull() {
        assertNotNull(webClientBuilder);
    }

    //web security configuration test

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testSecurityConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/buildings"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}