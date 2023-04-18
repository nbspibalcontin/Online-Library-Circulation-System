package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import Services.NotificationService;

@RestController
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/noti/{userid}")
	public ResponseEntity<?> notification(@PathVariable Long userid) {
		try {
			return new ResponseEntity<>(notificationService.findByStatus(userid), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/noti/update/{userid}")
	public ResponseEntity<?> notificationupdate(@PathVariable Long userid) {
		try {
			return new ResponseEntity<>(notificationService.Updatedata(userid), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
