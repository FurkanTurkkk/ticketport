package com.furkan.ticketport.event.dto.response;

import com.furkan.ticketport.event.model.Session;

import java.util.List;

public record SessionsListResponse(List<SessionResponse> sessions) {

    public static SessionsListResponse from(List<Session> sessions) {
        return new SessionsListResponse(sessions.stream().map(SessionResponse::from).toList());
    }
}
