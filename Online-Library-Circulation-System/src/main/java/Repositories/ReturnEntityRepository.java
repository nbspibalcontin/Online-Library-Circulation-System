package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Returnentity;

public interface ReturnEntityRepository extends JpaRepository<Returnentity, Long> {

	Returnentity findByStudentID(String studentID);

	Returnentity findByid(Long BookId);
}
