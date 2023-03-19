package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Approveentity;

public interface ApproveentityRepository extends JpaRepository<Approveentity, Long> {

	Approveentity findByStudentID(String studentID);
}
