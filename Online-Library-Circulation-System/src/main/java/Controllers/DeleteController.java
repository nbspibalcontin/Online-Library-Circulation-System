package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Reponses.MessageResponse;
import Services.DeleteServices.DeleteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RequestMapping("/api")
public class DeleteController {

	@Autowired
	private DeleteService deleteService;

	// Delete Book

	@DeleteMapping("/deletebook/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteBook(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete Reserve

	@DeleteMapping("/deletereserve/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> deleteReserve(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteReserve(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete Book Lost

	@DeleteMapping("/deletebooklost/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteBooklost(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteBookLost(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete User

	@DeleteMapping("/deleteuser/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteUser(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete Approve

	@DeleteMapping("/deleteapprove/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteApprove(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteApprove(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete Received

	@DeleteMapping("/deletereceived/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteReceived(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteReceived(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete Return

	@DeleteMapping("/deletereturn/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteReturn(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteReturn(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// Delete SuccessfulTransaction

	@DeleteMapping("/deletesuccess/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteSuccessTransaction(@PathVariable Long id) {
		try {
			MessageResponse messageResponse = deleteService.DeleteSucecssTransaction(id);

			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}
}
