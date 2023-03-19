package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.Userentity;
import jakarta.transaction.Transactional;

public interface UserEntityRepository extends JpaRepository<Userentity, Long> {

	Optional<Userentity> findByEmail(String email);

	Boolean existsByEmail(String email);

	Userentity findByStudentID(String studentID);

	Boolean existsByStudentID(String studentID);

	@Modifying
	@Transactional
	@Query(value = "UPDATE User_entity u set u.books_borrowed = u.books_borrowed + 1 where u.studentID = :studentID", nativeQuery = true)
	int UpdateBorrowedStudentLimit(@Param("studentID") String studentID);
}
