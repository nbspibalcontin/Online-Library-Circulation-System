package Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import Requests.SearchBook;

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

			// Custom HttpHeader
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

			// Find the Reserve by Id
			Reserveentity reserve = reserveEntityRepository.findByid(id);

			// Find the Book by BookId
			Bookentity book = bookEntityRepository.findBybookId(reserve.getBookId());

			// Find the Student by StudentID
			Userentity student = userEntityRepository.findByStudentID(reserve.getStudentID());

			// Save the key data's of ApproveEntity
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

			// Find the Approved by Id
			Approveentity approveentity = approveentityRepository.findByid(id);

			// LocalDateandTime
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime dueDate = now.plusDays(3);

			// Save the key data's of ReceivedBookEntity
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

			// Custom HttpHeaders
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			// Find the Received by Id
			ReceivedBook received = receivedBookRepository.findByid(id);

			// Find the Book by BookId
			Bookentity book = bookEntityRepository.findBybookId(received.getBookId());

			// Find the Student by StudentID
			Userentity student = userEntityRepository.findByStudentID(received.getStudentID());

			// Save the key data's of ReturnEntity
			Returnentity returnentity = new Returnentity();

			// LocalDateandTime
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

	// SUCCESSFUL TRANSACTION //

	public ResponseEntity<?> SuccessfulTransaction(Long id) {
		try {

			// Find the ReturnEntity by Id
			Returnentity returned = returnEntityRepository.findByid(id);

			// Find the Student by StudentID
			Userentity student = userEntityRepository.findByStudentID(returned.getStudentID());

			// Save the key data's of SuccessfulEntity
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

	// SEARCH BOOK //

	public ResponseEntity<?> SearchBook(SearchBook searchBook) {
		try {

			List<Bookentity> response = new ArrayList<>();

			switch (searchBook.getFilter()) {

			case "title":
				response = bookEntityRepository.searchBytitle(searchBook.getKeyword());
				break;

			case "author":
				response = bookEntityRepository.searchByauthor(searchBook.getKeyword());
				break;

			case "subject":
				response = bookEntityRepository.searchBysubject(searchBook.getKeyword());
				break;

			case "datepublish":
				response = bookEntityRepository.searchBydatepublish(searchBook.getKeyword());
				break;

			default:
				response = bookEntityRepository.findAll();
				break;
			}

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "No Bearer token");

			if (response.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("Book not found!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
			}
		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// ------------------------FindAll---------------------------//

	// GET ALL SUCCESSFUL TRANSACTION //

	public List<Successfulentity> getAllSuccessfulTransaction() {
		return successfulEntityRepository.findAll();
	}

	// GET ALL BOOKS //

	public List<Bookentity> getAllBooks() {
		return bookEntityRepository.findAll();
	}

	// GET ALL APPROVAL REQUEST //

	public List<Approveentity> getAllApprovalRequest() {
		return approveentityRepository.findAll();
	}

	// GET ALL RECEIVED BOOKS //

	public List<ReceivedBook> getAllReceivedBooks() {
		return receivedBookRepository.findAll();
	}

	// GET ALL RETURNED BOOKS //

	public List<Returnentity> getAllReturnedBooks() {
		return returnEntityRepository.findAll();
	}

	// ------------------------FindBy---------------------------//

	// GET SUCCESSFUL TRANSACTION BY ID //

	public Successfulentity getSuccessfulTransactionById(Long id) {
		return successfulEntityRepository.findByid(id);
	}

	// GET APPROVAL REQUEST BY ID //

	public Approveentity getApprovalRequestById(Long id) {
		return approveentityRepository.findByid(id);
	}

	// GET RECEIVED BOOKS BY ID //

	public ReceivedBook getReceivedBooksById(Long id) {
		return receivedBookRepository.findByid(id);
	}

	// GET BOOKS BY ID //

	public Bookentity getBooksById(Long id) {
		return bookEntityRepository.findByid(id);
	}

	// GET RETURNED BOOKS BY ID //

	public Returnentity getReturnedBooksById(Long id) {
		return returnEntityRepository.findByid(id);
	}

}
