package Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Entities.Approveentity;
import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Successfulentity;
import Entities.Userentity;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;

@Service
public class BookService {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ApproveentityRepository approveentityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private ReceivedBookRepository receivedBookRepository;

	@Autowired
	private ReturnEntityRepository returnEntityRepository;

	@Autowired
	private SuccessfulEntityRepository successfulEntityRepository;

	private static final double DAILY_FINE_AMOUNT = 30.50;

	// ADD BOOK //
	public ResponseEntity<?> createBook(Bookentity bookentity) {
		try {

			bookEntityRepository.save(bookentity);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Book created successfully!");
		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}

	}

	// APPROVE THE BOOK //

	public ResponseEntity<?> ApproveTheBook(Long id) {
		try {

			Reserveentity reserve = reserveEntityRepository.findByid(id);

			Bookentity book = bookEntityRepository.findBybookId(reserve.getBookId());

			Userentity student = userEntityRepository.findByStudentID(reserve.getStudentID());

			Approveentity approveentity = new Approveentity();
			approveentity.setBooktitle(reserve.getBooktitle());
			approveentity.setCourse(reserve.getCourse());
			approveentity.setDepartment(reserve.getDepartment());
			approveentity.setEmail(reserve.getEmail());
			approveentity.setFirstname(reserve.getFirstname());
			approveentity.setLastname(reserve.getLastname());
			approveentity.setStudentID(reserve.getStudentID());
			approveentity.setBookId(book.getBookId());
			approveentity.setStatus("Approved");

			approveentityRepository.save(approveentity);
			bookEntityRepository.UpdateQuantityBook(book.getBookId());
			userEntityRepository.UpdateBorrowedStudentLimit(student.getStudentID());
			reserveEntityRepository.deleteById(reserve.getId());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Book approved successfully!");
		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}

	}

	// RECEIVED THE BOOK //

	public ResponseEntity<?> ReceivedTheBook(Long id) {
		try {

			Approveentity approveentity = approveentityRepository.findByid(id);

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime dueDate = now.plusDays(3);

			ReceivedBook receivedBook = new ReceivedBook();

			receivedBook.setStatus("Borrowed");
			receivedBook.setDueDate(dueDate);
			receivedBook.setBookId(approveentity.getBookId());
			receivedBook.setStudentID(approveentity.getStudentID());

			receivedBookRepository.save(receivedBook);
			approveentityRepository.deleteById(id);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			return ResponseEntity.status(HttpStatus.OK).headers(headers)
					.body("Student received the book successfully!");

		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// RETURN //
	// DUE DATA AND FINES //

	public ResponseEntity<?> ReturnAndCalculateFines(Long id) {
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			ReceivedBook received = receivedBookRepository.findByid(id);

			Bookentity book = bookEntityRepository.findBybookId(received.getBookId());

			Userentity student = userEntityRepository.findByStudentID(received.getStudentID());

			Returnentity returnentity = new Returnentity();

			LocalDateTime dueDate = received.getDueDate();
			LocalDateTime currentDate = LocalDateTime.now();

			// Calculate the difference between due date and return date
			Duration duration = Duration.between(dueDate, currentDate);

			// Calculate the number of days overdue
			long daysOverdue = duration.toDays();

			double fines = DAILY_FINE_AMOUNT * daysOverdue;

			if (fines > 0) {
				returnentity.setDueDate(received.getDueDate());
				returnentity.setBookId(received.getBookId());
				returnentity.setStudentID(received.getStudentID());
				returnentity.setStatus("Returned");
				returnentity.setFines(fines);
				returnentity.setReturnDate(currentDate);
			} else {
				returnentity.setDueDate(received.getDueDate());
				returnentity.setBookId(received.getBookId());
				returnentity.setStudentID(received.getStudentID());
				returnentity.setStatus("Returned");
				returnentity.setFines(0);
				returnentity.setReturnDate(currentDate);
			}

			returnEntityRepository.save(returnentity);
			bookEntityRepository.UpdateQuantityBook1(book.getBookId());
			userEntityRepository.UpdateBorrowedStudentLimit1(student.getStudentID());
			receivedBookRepository.deleteById(id);

			// Return the fines as a response
			if (fines > 0) {
				String message = "Book returned late. Fines: $" + String.format("%.2f", fines);
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(message);
			} else {
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Book returned successfully.");
			}
		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	public ResponseEntity<?> SuccessfulTransaction(Long id) {
		try {

			Returnentity returned = returnEntityRepository.findByid(id);

			Userentity student = userEntityRepository.findByStudentID(returned.getStudentID());

			Successfulentity successfulentity = new Successfulentity();
			successfulentity.setBookId(returned.getBookId());
			successfulentity.setCourse(student.getCourse());
			successfulentity.setDepartment(student.getDepartment());
			successfulentity.setDueDate(returned.getDueDate());
			successfulentity.setEmail(student.getEmail());
			successfulentity.setFirstname(student.getFirstname());
			successfulentity.setLastname(student.getLastname());
			successfulentity.setReturnDate(returned.getReturnDate());
			successfulentity.setStudentID(student.getStudentID());

			successfulEntityRepository.save(successfulentity);
			returnEntityRepository.deleteById(id);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");
			
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Book transaction successfully.");

		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}

	}

	// GET SUCCESSFUL TRANSACTION //
	public List<Successfulentity> getAllSuccessfulTransaction() {
		return successfulEntityRepository.findAll();
	}

}
