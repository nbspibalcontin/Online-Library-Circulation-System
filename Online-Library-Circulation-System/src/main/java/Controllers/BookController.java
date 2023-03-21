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
import Entities.Returnentity;
import Entities.Successfulentity;
import Reponses.MessageResponse;
import Services.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;

	// LIST OF BOOKS //

	@GetMapping("/successlist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllSuccessTransaction() {

		List<Successfulentity> data = bookService.getAllSuccessfulTransaction();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/booklist")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllBooks() {

		List<Bookentity> data = bookService.getAllBooks();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/approvelist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllApprove() {

		List<Approveentity> data = bookService.getAllApprovalRequest();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/receivedlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReceived() {

		List<ReceivedBook> data = bookService.getAllReceivedBooks();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/returnedlist")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReturned() {

		List<Returnentity> data = bookService.getAllReturnedBooks();

		if (data.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("No data in database"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	// GET BY ID //

	@GetMapping("/successlist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllSuccessTransactionById(@PathVariable Long id) {

		Successfulentity data = bookService.getSuccessfulTransactionById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/booklist/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> getAllBooksById(@PathVariable Long id) {

		Bookentity data = bookService.getBooksById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/approvelist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllApproveById(@PathVariable Long id) {

		Approveentity data = bookService.getApprovalRequestById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/receivedlist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReceivedById(@PathVariable Long id) {

		ReceivedBook data = bookService.getReceivedBooksById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/returnedlist/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAllReturnedById(@PathVariable Long id) {

		Returnentity data = bookService.getReturnedBooksById(id);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Cannot find the ID!"));
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// UPDATE //

}
