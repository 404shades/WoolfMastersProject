package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scaler.project.ecommercemasterbackendproject.dtos.LoginRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.LoginResponseDTO;
import scaler.project.ecommercemasterbackendproject.dtos.SignUpRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.InvalidRoleException;
import scaler.project.ecommercemasterbackendproject.exceptions.UserAlreadyExistsException;
import scaler.project.ecommercemasterbackendproject.models.Role;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;
import scaler.project.ecommercemasterbackendproject.repositories.RoleRepository;
import scaler.project.ecommercemasterbackendproject.repositories.UserRepository;

@Service
public class SelfAuthService implements AuthService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    TokenService tokenService;

    public SelfAuthService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public UserResponseDTO signUp(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException, InvalidRoleException {
        RoleName roleName = signUpRequest.getRole() == null ? RoleName.CUSTOMER : signUpRequest.getRole();
        // admins are only created by the seeder, otherwise anyone could make themselves admin
        if (roleName == RoleName.ADMIN) {
            throw new InvalidRoleException(roleName);
        }

        String email = normalizeEmail(signUpRequest.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Role " + roleName + " is missing in db"));

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.getRoles().add(role);
        return UserResponseDTO.from(userRepository.save(user));
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // throws BadCredentialsException if email or password is wrong
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizeEmail(loginRequest.getEmail()), loginRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setAccessToken(tokenService.generateToken(userDetails));
        loginResponse.setTokenType("Bearer");
        loginResponse.setExpiresInMs(tokenService.getExpirationMs());
        return loginResponse;
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
