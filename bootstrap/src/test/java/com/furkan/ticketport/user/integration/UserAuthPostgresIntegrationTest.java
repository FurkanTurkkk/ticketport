package com.furkan.ticketport.user.integration;

import com.furkan.ticketport.TicketPortApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Gerçek PostgreSQL (Docker) ile uçtan uca doğrulama: Flyway, JPA, Security permitAll, auth API.
 * Docker yoksa bu sınıf Testcontainers başlatamadığı için hata verir; bu entegrasyon testinin bilinçli maliyeti.
 */
@SpringBootTest(classes = TicketPortApplication.class)
@AutoConfigureMockMvc
@Testcontainers
class UserAuthPostgresIntegrationTest {

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

    @Test
    void registerLoginAndRejectDuplicateEmail() throws Exception {
        String email = "it-" + UUID.randomUUID() + "@integration.test";
        String bodyRegister =
                """
                {"email":"%s","password":"SecureP1!"}
                """
                        .formatted(email);
        String bodyLogin =
                """
                {"email":"%s","rawPassword":"SecureP1!"}
                """
                        .formatted(email);

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyRegister))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists());

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyLogin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyRegister))
                .andExpect(status().isConflict());
    }
}
