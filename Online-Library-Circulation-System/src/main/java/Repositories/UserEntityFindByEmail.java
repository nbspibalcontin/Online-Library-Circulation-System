package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.Userentity;

public interface UserEntityFindByEmail extends JpaRepository<Userentity, Long> {

	Userentity findByEmail(String email);

}
