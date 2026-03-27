package com.furkan.ticketport.event.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furkan.ticketport.TicketPortApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PostgreSQL + Flyway + JPA ile etkinlik akışı: admin JWT, oluştur, slug ata, yayınla, seans, listeler.
 */
@SpringBootTest(classes = TicketPortApplication.class)
@AutoConfigureMockMvc
@Testcontainers
class EventCatalogPostgresIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adminCreatesPublishesAndListsSessions() throws Exception {
        String token = adminToken();

        String title = "IT Event " + UUID.randomUUID();
        String createBody =
                """
                {"categoryId":"b0000001-0000-4000-8000-000000000001","title":"%s","description":"d","coverImageRef":null}
                """
                        .formatted(title);

        MvcResult created =
                mockMvc.perform(
                                post("/api/v1/events")
                                        .header("Authorization", "Bearer " + token)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.eventId").exists())
                        .andReturn();
        String eventId =
                objectMapper
                        .readTree(created.getResponse().getContentAsString())
                        .get("eventId")
                        .asText();

        mockMvc.perform(
                        put("/api/v1/events/" + eventId + "/slug")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"slug\":\"custom-" + UUID.randomUUID() + "\"}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        post("/api/v1/events/" + eventId + "/publish")
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        String sessionBody =
                """
                {"eventId":"%s","capacity":25,"startedAt":"2035-06-01T10:00:00Z","endsAt":"2035-06-01T12:00:00Z"}
                """
                        .formatted(eventId);
        mockMvc.perform(
                        post("/api/v1/sessions")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sessionBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").exists());

        mockMvc.perform(
                        get("/api/v1/sessions")
                                .param("eventId", eventId)
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessions").isArray());

        mockMvc.perform(get("/api/v1/events/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events").isArray());
    }

    private String adminToken() throws Exception {
        MvcResult login =
                mockMvc.perform(
                                post("/api/v1/auth/login")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(
                                                """
                                                {"email":"admin@ticketport.local","rawPassword":"Admin1!Pass"}
                                                """))
                        .andExpect(status().isOk())
                        .andReturn();
        JsonNode tree = objectMapper.readTree(login.getResponse().getContentAsString());
        return tree.get("token").asText();
    }
}
