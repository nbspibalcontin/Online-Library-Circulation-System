package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Reserveentity;
import Entities.Userentity;
import Reponses.MessageResponse;
import Services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	// LIST OF USERS //

	@GetMapping("/userlist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllUser() {

		List<Userentity> data = userService.getAllUsers();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	// LIST OF RESERVATION REQUEST //

	@GetMapping("/reservelist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllReserve() {

		List<Reserveentity> data = userService.getAllReservation();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	// LIST OF RESERVATION REQUEST //

	@GetMapping("/userlist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {

		Userentity data = userService.getStudentById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	// GET BY ID //

	@GetMapping("/reservelist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getReserveById(@PathVariable Long id) {

		Reserveentity data = userService.getReserveById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

}
