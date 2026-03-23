package com.furkan.ticketport.user.incoming;

import com.furkan.ticketport.user.dto.request.LoginUserRequest;
import com.furkan.ticketport.user.dto.request.RegisterUserRequest;
import com.furkan.ticketport.user.dto.response.LoginUserResponse;
import com.furkan.ticketport.user.dto.response.RegisterUserResponse;
import com.furkan.ticketport.user.port.in.LoginUserUseCase;
import com.furkan.ticketport.user.port.in.RegisterUserUseCase;
import com.furkan.ticketport.user.result.LoginResult;
import com.furkan.ticketport.user.valueobject.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public RegisterUserController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        UserId userId = registerUserUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new RegisterUserResponse(userId.asString()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
        LoginResult result = loginUserUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new LoginUserResponse(result.token()));
    }
}
