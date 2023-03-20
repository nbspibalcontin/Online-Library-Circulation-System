package Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.Bookentity;
import jakarta.transaction.Transactional;

public interface BookEntityRepository extends JpaRepository<Bookentity, Long> {

	@Query("SELECT b FROM Bookentity b WHERE b.title LIKE %:searchString%")
	List<Bookentity> searchBytitle(@Param("searchString") String keyword);

	@Query("SELECT b FROM Bookentity b WHERE b.author LIKE %:searchString%")
	List<Bookentity> searchByauthor(@Param("searchString") String keyword);

	@Query("SELECT b FROM Bookentity b WHERE b.subject LIKE %:searchString%")
	List<Bookentity> searchBysubject(@Param("searchString") String keyword);

	@Query("SELECT b FROM Bookentity b WHERE b.datepublish LIKE %:searchString%")
	List<Bookentity> searchBydatepublish(@Param("searchString") String keyword);

	Bookentity findBybookId(Long BookId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Book_entity b set b.quantity = b.quantity - 1 where b.book_id = :bookId", nativeQuery = true)
	int UpdateQuantityBook(@Param("bookId") Long bookId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Book_entity b set b.quantity = b.quantity + 1 where b.book_id = :bookId", nativeQuery = true)
	int UpdateQuantityBook1(@Param("bookId") Long bookId);

}
