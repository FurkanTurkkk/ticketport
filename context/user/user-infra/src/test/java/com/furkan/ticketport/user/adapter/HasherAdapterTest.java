package com.furkan.ticketport.user.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HasherAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private HasherAdapter adapter;

    @Test
    void hashDelegatesToEncoder() {
        when(passwordEncoder.encode("raw")).thenReturn("ENC");

        assertEquals("ENC", adapter.hash("raw"));
        verify(passwordEncoder).encode("raw");
    }

    @Test
    void matchDelegatesToEncoder() {
        when(passwordEncoder.matches("raw", "ENC")).thenReturn(true);

        assertTrue(adapter.match("raw", "ENC"));
        verify(passwordEncoder).matches("raw", "ENC");
    }
}
