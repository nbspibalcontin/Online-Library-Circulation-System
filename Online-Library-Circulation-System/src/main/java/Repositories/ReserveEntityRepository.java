package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Reserveentity;

public interface ReserveEntityRepository extends JpaRepository<Reserveentity, Long> {

	Reserveentity findByBookIdAndStudentID(Long BookId, String StudentID);

	Reserveentity findByid(Long id);

	Reserveentity deleteByStudentID(String StudentID);

	boolean existsById(Long id);
}
