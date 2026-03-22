package com.furkan.ticketport.incoming;

import com.furkan.ticketport.dto.request.RegisterUserRequest;
import com.furkan.ticketport.dto.response.RegisterUserResponse;
import com.furkan.ticketport.port.in.RegisterUserUseCase;
import com.furkan.ticketport.valueobject.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        UserId userId = registerUserUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new RegisterUserResponse(userId.asString()));
    }
}
