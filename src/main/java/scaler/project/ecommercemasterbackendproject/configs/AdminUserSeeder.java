package scaler.project.ecommercemasterbackendproject.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scaler.project.ecommercemasterbackendproject.models.Role;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;
import scaler.project.ecommercemasterbackendproject.repositories.RoleRepository;
import scaler.project.ecommercemasterbackendproject.repositories.UserRepository;

// signup never gives ADMIN, so the first admin is created here on startup
@Component
public class AdminUserSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminUserSeeder.class);

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    String adminEmail;

    @Value("${admin.password}")
    String adminPassword;

    public AdminUserSeeder(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("ADMIN role is missing, check V4 migration"));

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
        log.info("Created default admin user {}", adminEmail);
    }
}
