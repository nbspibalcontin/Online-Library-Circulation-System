package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Approveentity;

public interface ApproveentityRepository extends JpaRepository<Approveentity, Long> {

	Approveentity findByid(Long BookId);

	boolean existsByBookIdAndStudentID(Long bookId, String studentID);
	
	boolean existsById(Long id);
}
