package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByStudentID(String studentID);
}

