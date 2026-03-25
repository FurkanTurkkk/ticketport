package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.DeleteSessionUseCase;
import com.furkan.ticketport.event.port.out.session.SessionPersistencePort;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.stereotype.Service;

@Service
public class DeleteSessionService implements DeleteSessionUseCase {

    private final SessionQueryPort sessionQueryPort;
    private final SessionPersistencePort sessionPersistencePort;

    public DeleteSessionService(SessionQueryPort sessionQueryPort, SessionPersistencePort sessionPersistencePort) {
        this.sessionQueryPort = sessionQueryPort;
        this.sessionPersistencePort = sessionPersistencePort;
    }

    @Override
    public void execute(SessionId sessionId) {
        Session session = requireExistingSession(sessionId);
        sessionPersistencePort.delete(session);
    }

    private Session requireExistingSession(SessionId sessionId) {
        return sessionQueryPort.findBySessionId(sessionId)
                .orElseThrow(
                        () ->
                                new SessionNotFoundException("Session not found : " + sessionId.asString())
                );
    }

}
