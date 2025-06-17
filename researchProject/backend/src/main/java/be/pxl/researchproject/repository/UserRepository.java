package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndHashedPassword(String email, String hashedPassword);
    User findUserByEmail(String email);
    User findByResetPasswordToken(String token);

}
