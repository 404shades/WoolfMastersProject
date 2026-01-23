package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.Role;
import scaler.project.ecommercemasterbackendproject.models.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
