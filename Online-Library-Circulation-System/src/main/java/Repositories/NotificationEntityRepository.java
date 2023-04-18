package Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Entities.NotificationEntity;
import jakarta.transaction.Transactional;

public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long>{

	NotificationEntity findByid(long id);
	
	//Count Notifications
	@Query(value = "SELECT COUNT(status) AS totalcount FROM notification_entity WHERE user_id = ?1 AND status = 1", nativeQuery = true)
	long count(long userid);

	//Show notifications
	@Query(value = "SELECT * FROM notification_entity WHERE user_id = ?1 ORDER BY id DESC", nativeQuery = true)
	List<NotificationEntity> counts(long userid);

	@Modifying
	@Transactional
	@Query(value = "UPDATE notification_entity SET status='0' WHERE user_id = :userid", nativeQuery = true)
	Integer findByuserid(long userid);
}
