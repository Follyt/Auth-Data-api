package com.auth.aService.Controller.Auth;


import com.auth.aService.DTO.Request.LoginRequest;
import com.auth.aService.DTO.Request.RegisterRequest;
import com.auth.aService.DTO.Response.UserResponse;
import com.auth.aService.Model.User;
import com.auth.aService.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public UserResponse register(@RequestBody @Valid RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(request.password_hash());
        User saved = authService.register(user);
        return new UserResponse(saved.getId(), saved.getEmail());
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Valid LoginRequest request) {
        // вернём сырой JWT токен для проверки генерации
        return authService.login(request.email(), request.password_hash());
    }

}
