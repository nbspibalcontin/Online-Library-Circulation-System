package Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Userentity;
import Reponses.ErrorResponse;
import Reponses.MessageResponse;
import Repositories.BookEntityRepository;
import Repositories.ReserveEntityRepository;
import Repositories.UserEntityRepository;
import Requests.ReserveRequest;
import Services.BookService;
import Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

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
	public ResponseEntity<?> SaveBook(@Valid @RequestBody Bookentity bookEntity, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors));
		}

		if (bookEntity == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Can't be empty"));
		}

		return bookService.createBook(bookEntity);

	}

	// -----APPROVE THE RESERVATION OF BOOK----//

	@PostMapping("/approve/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ApproveTheRequest(@PathVariable Long id) {

		boolean bookExists = reserveEntityRepository.existsById(id);

		if (!bookExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("ID doesn't exist! in reserve request"));
		}

		Reserveentity reserve = reserveEntityRepository.findByid(id);

		Bookentity book = bookEntityRepository.findBybookId(reserve.getBookId());

		Userentity student = userEntityRepository.findByStudentID(reserve.getStudentID());

		if (book.getQuantity() == 0) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Currently, the book is not available."));
		}

		// check if the student has already borrowed 3 books
		if (student.getBooksBorrowed() >= 3) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Student has already borrowed 3 books"));
		}

		return bookService.ApproveTheBook(id);

	}

	// RECEIVED THE BOOK //

	@PostMapping("/received")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ReceivedTheBook(@Valid @RequestBody ReceivedBook receivedBook,
			BindingResult bindingResult) {

		Bookentity book = bookEntityRepository.findBybookId(receivedBook.getBookId());

		Userentity student = userEntityRepository.findByStudentID(receivedBook.getStudentID());

		if (book == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the book!"));
		}

		if (student == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the Student!"));
		}

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors));
		}

		return bookService.ReceivedTheBook(receivedBook);

	}

	// RETURN //
	// DUE DATE THE BOOK AND FINES //
	@PostMapping("/return")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ReturnTheBook(@RequestBody Returnentity returnentity) {

		return bookService.ReturnAndCalculateFines(returnentity);

	}

	// ------------------------USER---------------------------//

	// RESERVE THE BOOK //

	@PostMapping("/reserve")
	public ResponseEntity<?> ReserveBook(@Valid @RequestBody ReserveRequest reserveRequest,
			BindingResult bindingResult) {

		Bookentity book = bookEntityRepository.findBybookId(reserveRequest.getBookId());

		Userentity student = userEntityRepository.findByStudentID(reserveRequest.getStudentID());

		if (book == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the book!"));
		}

		if (student == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the Student!"));
		}

		if (book.getQuantity() == 0) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Currently, the book is not available."));
		}

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors));
		}

		// check if the student has already borrowed 3 books
		if (student.getBooksBorrowed() >= 3) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("Student has already borrowed 3 books"));
		}

		// Check if the book is already reserved by the same student
		Reserveentity existingReservation = reserveEntityRepository.findByBookIdAndStudentID(reserveRequest.getBookId(),
				reserveRequest.getStudentID());
		if (existingReservation != null) {
			// Book is already reserved by the same student, return a 409 Conflict response
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new MessageResponse("You already reserved this book"));
		}

		return userService.ReserveBook(reserveRequest);

	}
}
