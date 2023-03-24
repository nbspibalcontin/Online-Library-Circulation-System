package Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Approveentity;
import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Userentity;
import ExeptionHandler.ConflictException;
import ExeptionHandler.CreationException;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.ErrorResponse;
import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.UserEntityRepository;
import Requests.BookRequest;
import Requests.ReserveRequest;
import Services.CreateServices.AddService;
import Services.CreateServices.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class MainController {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private ApproveentityRepository approveentityRepository;

	@Autowired
	private ReceivedBookRepository receivedBookRepository;

	@Autowired
	private ReturnEntityRepository returnEntityRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AddService addService;

//	@PostMapping("/url")
//	public ResponseEntity<?> create(@RequestBody Dto dto) {
//		try {
//			//TODO Implement Your Logic To Save Data And Return Result Through ResponseEntity
//			return new ResponseEntity<>("Create Result", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	// ------------------------ADMIN---------------------------//

	// ADD BOOK //

	@PostMapping("/addbook")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> SaveBook(@Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			Optional<Bookentity> existingBook = bookEntityRepository.findBybookId(bookRequest.getBookId());
			if (existingBook.isPresent()) {
				throw new CreationException("Error: Book with ID " + bookRequest.getBookId() + " already exists");
			}

			MessageResponse messageResponse = addService.createBook(bookRequest);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (CreationException e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// -----APPROVE THE RESERVATION OF BOOK----//

	@PostMapping("/approve/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> approveTheRequest(@PathVariable Long id) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {
			// Find the reserve by ID
			Reserveentity reserve = reserveEntityRepository.findByid(id);
			if (reserve == null) {
				throw new NotFoundException("Reserve request with ID " + id + " not found.");
			}

			// Find the book by book ID
			Bookentity book = bookEntityRepository.findByBookId(reserve.getBookId());
			if (book == null) {
				throw new NotFoundException("Book not found with ID: " + reserve.getBookId());
			}
			// Find the student by student ID
			Userentity student = userEntityRepository.findByStudentID(reserve.getStudentID());
			if (student == null) {
				throw new NotFoundException("Book not found with ID: " + reserve.getStudentID());
			}

			// Check if the book is available
			if (book.getQuantity() == 0) {
				throw new ConflictException("Currently, the book is not available.");
			}

			// Check if the student has already borrowed 3 books
			if (student.getBooksBorrowed() >= 3) {
				throw new ConflictException("Student has already borrowed 3 books");
			}

			// Approve the book
			MessageResponse messageResponse = transactionService.approveReservedBook(id);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			// Log the error message
			log.error("Error while approving book with id " + id + ": " + e.getMessage(), e);

			// Return an error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An error occurred while approving the book: " + e.getMessage()));
		}
	}

	// RECEIVED THE BOOK //

	@PostMapping("/received/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ReceivedTheBook(@PathVariable Long id) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {
			boolean bookExists = approveentityRepository.existsById(id);
			if (!bookExists) {
				throw new NotFoundException("Approve request with ID " + id + " not found.");
			}

			Approveentity approveentity = approveentityRepository.findByid(id);
			if (approveentity == null) {
				throw new NotFoundException("Approve Request not found with ID: " + id);
			}

			Bookentity book = bookEntityRepository.findByBookId(approveentity.getBookId());
			if (book == null) {
				throw new NotFoundException("Book not found with ID: " + approveentity.getBookId());
			}

			Userentity student = userEntityRepository.findByStudentID(approveentity.getStudentID());
			if (student == null) {
				throw new NotFoundException("Student not found with ID: " + approveentity.getStudentID());
			}

			MessageResponse messageResponse = transactionService.receiveTheBook(id);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// RETURN //
	// DUE DATE THE BOOK AND FINES //

	@PostMapping("/return/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ReturnTheBook(@PathVariable Long id) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {
			boolean bookExists = receivedBookRepository.existsById(id);
			if (!bookExists) {
				throw new NotFoundException("Received request with ID " + id + " not found.");
			}

			ReceivedBook received = receivedBookRepository.findByid(id);
			if (received == null) {
				throw new NotFoundException("Received Request not found with ID: " + id);
			}

			Bookentity book = bookEntityRepository.findByBookId(received.getBookId());
			if (book == null) {
				throw new NotFoundException("Book not found with ID: " + received.getBookId());
			}

			ReceivedBook receivedBook = receivedBookRepository.findByStudentID(received.getStudentID());
			if (receivedBook == null) {
				throw new NotFoundException("Student not found with ID: " + received.getStudentID());
			}

			MessageResponse messageResponse = transactionService.ReturnAndCalculateFines(id);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// SUCCESSFUL TRANSACTION //

	@PostMapping("/successful/{id}")
	public ResponseEntity<?> successfulTransaction(@PathVariable Long id) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {
			boolean bookExists = returnEntityRepository.existsById(id);
			if (!bookExists) {
				throw new NotFoundException("Return request with ID " + id + " not found.");
			}

			MessageResponse messageResponse = transactionService.successfulTransaction(id);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An error occurred: " + e.getMessage()));
		}
	}

	// ------------------------USER---------------------------//

	// RESERVE THE BOOK //

	@PostMapping("/reserve")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> ReserveBook(@Valid @RequestBody ReserveRequest reserveRequest,
			BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		try {
			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			Bookentity book = bookEntityRepository.findByBookId(reserveRequest.getBookId());
			if (book == null) {
				throw new NotFoundException("Book not found with ID: " + reserveRequest.getBookId());
			}

			boolean receivedexists = receivedBookRepository.existsByBookIdAndStudentID(reserveRequest.getBookId(),
					reserveRequest.getStudentID());
			if (receivedexists) {
				throw new ConflictException("You have already received the book.");
			}

			Userentity student = userEntityRepository.findByStudentID(reserveRequest.getStudentID());
			if (student == null) {
				throw new NotFoundException("Student not found with ID: " + reserveRequest.getStudentID());
			}

			boolean exists = approveentityRepository.existsByBookIdAndStudentID(reserveRequest.getBookId(),
					reserveRequest.getStudentID());
			if (exists) {
				throw new ConflictException(
						"This book is already the approve. Please wait go to library to received the book.");
			}

			if (book.getQuantity() == 0) {
				throw new ConflictException("The book is currently not available.");
			}

			// check if the student has already borrowed 3 books
			if (student.getBooksBorrowed() >= 3) {
				throw new ConflictException("Student has already borrowed 3 books");
			}

			// Check if the book is already reserved by the same student
			Reserveentity existingReservation = reserveEntityRepository
					.findByBookIdAndStudentID(reserveRequest.getBookId(), reserveRequest.getStudentID());
			if (existingReservation != null) {
				throw new ConflictException("You already reserved this book");
			}

			MessageResponse messageResponse = transactionService.ReserveBook(reserveRequest);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (ConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
		}

	}

}
