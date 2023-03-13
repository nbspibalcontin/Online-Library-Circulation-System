package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Userentity;

public interface UserEntityRepository extends JpaRepository<Userentity, Long>{
	
	Optional<Userentity> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	Userentity findByStudentID(String studentID);
	
	Boolean existsByStudentID(String studentID);
}

