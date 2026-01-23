package scaler.project.ecommercemasterbackendproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.services.UserService;

import java.util.List;

// everything under /admin is ADMIN only, see SecurityConfig
@RestController()
@RequestMapping("/admin")
public class AdminController {
    UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers(@RequestParam(required = false) RoleName role) {
        return userService.getAllUsers(role);
    }
}
