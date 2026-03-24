package com.furkan.ticketport.user.incoming;

import com.furkan.ticketport.user.port.in.LoginUserUseCase;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegisterUserControllerTest {

    @Mock
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private LoginUserUseCase loginUserUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(new RegisterUserController(registerUserUseCase, loginUserUseCase))
                        .build();
    }

    @Test
    void registerReturnsUserId() throws Exception {
        when(registerUserUseCase.execute(any())).thenReturn(UserId.valueOf("uid-1"));

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {"email":"api@user.com","password":"SecureP1!"}
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("uid-1"));
    }

    @Test
    void loginReturnsToken() throws Exception {
        when(loginUserUseCase.execute(any())).thenReturn(new LoginResult("tok"));

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                        {"email":"api@user.com","rawPassword":"SecureP1!"}
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("tok"));
    }
}
