package scaler.project.ecommercemasterbackendproject.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scaler.project.ecommercemasterbackendproject.dtos.LoginRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.LoginResponseDTO;
import scaler.project.ecommercemasterbackendproject.dtos.SignUpRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.InvalidRoleException;
import scaler.project.ecommercemasterbackendproject.exceptions.UserAlreadyExistsException;
import scaler.project.ecommercemasterbackendproject.services.AuthService;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequest)
            throws UserAlreadyExistsException, InvalidRoleException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
}
