package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Userentity;

public interface UserEntityRepository extends JpaRepository<Userentity, Long>{
	
	Optional<Userentity> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByStudentID(String studentID);
}

