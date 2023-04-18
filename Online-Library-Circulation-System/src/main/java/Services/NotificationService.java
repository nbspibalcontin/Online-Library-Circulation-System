package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Repositories.NotificationEntityRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationEntityRepository notificationEntityRepository;
	
	public long findByStatus(long userid) {
		return notificationEntityRepository.count(userid);
		
	}
	
	public Integer Updatedata(Long userid) {
		return notificationEntityRepository.findByuserid(userid);
		
	}
}
