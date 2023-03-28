package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Imageentity;

public interface ImageentityRepository extends JpaRepository<Imageentity, Long>{

	Imageentity findByUser(Long id);
}
