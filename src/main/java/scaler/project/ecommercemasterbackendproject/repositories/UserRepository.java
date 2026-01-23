package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRoles_Name(RoleName roleName);
}
