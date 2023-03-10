package Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.BookEntity;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long>{

	List<BookEntity> findByTitle(String keyword);

	List<BookEntity> findByAuthor(String keyword);
	
	List<BookEntity> findBySubject(String keyword);
	
	List<BookEntity> findByDatepublish(String keyword);
}
