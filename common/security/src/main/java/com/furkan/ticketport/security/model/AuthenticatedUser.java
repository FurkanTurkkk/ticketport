package com.furkan.ticketport.security.model;

public record AuthenticatedUser(String userId, String email, String role) {
}
