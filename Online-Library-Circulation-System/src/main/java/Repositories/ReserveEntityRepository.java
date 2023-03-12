package Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Reserveentity;

public interface ReserveEntityRepository extends JpaRepository<Reserveentity, Long>{

	Reserveentity findByBooktitleAndStudentID(String booktitle, String StudentID);
}
