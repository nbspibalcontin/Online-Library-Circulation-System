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

import Entities.Approveentity;
import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Successfulentity;
import Entities.Userentity;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.MessageResponse;
import Repositories.ReserveEntityRepository;
import Services.RetrieveServices.FindAllService;
import Services.RetrieveServices.FindByIdService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class RetrieveController {

	@Autowired
	ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private FindAllService findAllService;

	@Autowired
	private FindByIdService findByIdService;

	// ------------------------FINDALL---------------------------//

	// LIST OF SUCCESSFUL TRANSACTION //

	@GetMapping("/successlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllSuccessTransaction() {
		try {
			List<Successfulentity> data = findAllService.getAllSuccessfulTransaction();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF BOOK //

	@GetMapping("/booklist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllBooks() {
		try {
			List<Bookentity> data = findAllService.getAllBooks();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF APPROVE REQUEST //

	@GetMapping("/approvelist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllApprove() {
		try {
			List<Approveentity> data = findAllService.getAllApprovalRequest();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF RECEIVED //

	@GetMapping("/receivedlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReceived() {
		try {
			List<ReceivedBook> data = findAllService.getAllReceivedBooks();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF RETURN REQUEST //

	@GetMapping("/returnedlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReturned() {
		try {
			List<Returnentity> data = findAllService.getAllReturnedBooks();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF USERS //

	@GetMapping("/userlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllUser() {
		try {
			List<Userentity> data = findAllService.getAllUsers();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// LIST OF RESERVATION REQUEST //

	@GetMapping("/reservelist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllReserve() {
		try {
			List<Reserveentity> data = findAllService.getAllReservation();

			if (data.isEmpty()) {
				throw new NotFoundException("No data in database");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// ------------------------FINDBYID---------------------------//

	// GET SUCCESS TRANSACTION BY ID //

	@GetMapping("/successlist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllSuccessTransactionById(@PathVariable Long id) {
		try {
			Successfulentity data = findByIdService.getSuccessfulTransactionById(id);
			if (data == null) {
				throw new NotFoundException("Successful Transaction with ID " + id + " not found.");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET BOOK BY ID //

	@GetMapping("/booklist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllBooksById(@PathVariable Long id) {
		try {
			Bookentity data = findByIdService.getBooksById(id);
			if (data == null) {
				throw new NotFoundException("Book with ID " + id + " not found.");
			}

			return new ResponseEntity<>(data, HttpStatus.OK);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET APPROVE REQUEST BY ID //

	@GetMapping("/approvelist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllApproveById(@PathVariable Long id) {
		try {
			Approveentity data = findByIdService.getApprovalRequestById(id);
			if (data == null) {
				throw new NotFoundException("Approve request with ID " + id + " not found.");
			}
			
			return new ResponseEntity<>(data, HttpStatus.OK);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET RECEIVED BOOK BY ID //

	@GetMapping("/receivedlist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReceivedById(@PathVariable Long id) {
		try {
			ReceivedBook data = findByIdService.getReceivedBooksById(id);
			if (data == null) {
				throw new NotFoundException("Received book with ID " + id + " not found.");
			}
			
			return new ResponseEntity<>(data, HttpStatus.OK);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET RETURNED BOOK BY ID //

	@GetMapping("/returnedlist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReturnedById(@PathVariable Long id) {
		try {
			Returnentity data = findByIdService.getReturnedBooksById(id);
			if (data == null) {
				throw new NotFoundException("Returned book with ID " + id + " not found.");
			}
			
			return new ResponseEntity<>(data, HttpStatus.OK);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET USER BY ID //

	@GetMapping("/userlist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		try {
			Userentity data = findByIdService.getStudentById(id);
			if (data == null) {
				throw new NotFoundException("User with ID " + id + " not found.");
			}

			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// GET RESERVATION ID //

	@GetMapping("/reservelist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getReserveById(@PathVariable Long id) {
		try {
			Reserveentity data = findByIdService.getReserveById(id);
			if (data == null) {
				throw new NotFoundException("User with ID " + id + " not found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(data);
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}
}
