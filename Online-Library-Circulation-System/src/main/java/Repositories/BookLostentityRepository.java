package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.BookLostentity;

public interface BookLostentityRepository extends JpaRepository<BookLostentity, Long>{

	BookLostentity findByid(Long id);
}
