package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.Client;
import be.pxl.researchproject.domain.Foal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
