package scaler.project.ecommercemasterbackendproject.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.services.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    // logged in user, whatever role they have
    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserByEmail(userDetails.getUsername());
    }
}
