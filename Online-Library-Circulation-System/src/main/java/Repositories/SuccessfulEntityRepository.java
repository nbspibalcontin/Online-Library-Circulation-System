package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Successfulentity;

public interface SuccessfulEntityRepository extends JpaRepository<Successfulentity, Long>{

	boolean existsById(Long id);
}
