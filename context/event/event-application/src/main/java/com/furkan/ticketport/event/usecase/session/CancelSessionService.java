package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.command.session.CancelSessionCommand;
import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.CancelSessionUseCase;
import com.furkan.ticketport.event.port.out.session.SessionPersistencePort;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.stereotype.Service;

@Service
public class CancelSessionService implements CancelSessionUseCase {

    private final SessionQueryPort sessionQueryPort;
    private final SessionPersistencePort sessionPersistencePort;

    public CancelSessionService(SessionQueryPort sessionQueryPort, SessionPersistencePort sessionPersistencePort) {
        this.sessionQueryPort = sessionQueryPort;
        this.sessionPersistencePort = sessionPersistencePort;
    }

    @Override
    public void execute(CancelSessionCommand cmd) {
        Session session = requireExistingSession(cmd.sessionId());
        session.cancel();
        sessionPersistencePort.save(session);
    }

    public Session requireExistingSession(SessionId sessionId) {
        return sessionQueryPort.findBySessionId(sessionId)
                .orElseThrow(
                        () ->
                                new SessionNotFoundException("Session not found : " + sessionId.asString())
                );
    }
}
