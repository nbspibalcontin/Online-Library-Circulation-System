package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.ReceivedBook;
import jakarta.transaction.Transactional;

public interface ReceivedBookRepository extends JpaRepository<ReceivedBook, Long> {

	boolean existsById(Long id);

	ReceivedBook findByStudentID(String studentID);

	ReceivedBook findByid(Long id);

	ReceivedBook findByBookIdAndStudentID(String bookId, String studentID);

	@Transactional
	@Modifying
	@Query("DELETE FROM ReceivedBook r WHERE r.bookId = :bookId AND r.studentID = :studentID")
	Long deleteByBookIdAndStudentID(@Param("bookId") Long bookId, @Param("studentID") String studentID);
}
