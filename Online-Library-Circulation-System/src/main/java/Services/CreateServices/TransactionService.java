package Services.CreateServices;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Entities.Approveentity;
import Entities.BookLostentity;
import Entities.Bookentity;
import Entities.NotificationEntity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Successfulentity;
import Entities.Userentity;
import ExeptionHandler.BookTransactionException;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.BookLostentityRepository;
import Repositories.NotificationEntityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;
import Requests.BookLostRequest;
import Requests.ReserveRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

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
	private NotificationEntityRepository notificationEntityRepository;

	@Autowired
	private SuccessfulEntityRepository successfulEntityRepository;

	@Autowired
	private BookLostentityRepository bookLostentityRepository;

	private static final double DAILY_FINE_AMOUNT = 30.50;

	// APPROVE THE BOOK //

	public MessageResponse approveReservedBook(Long id) {

		Reserveentity reserve = reserveEntityRepository.findByid(id);
		if (reserve == null) {
			throw new NotFoundException("Reserve not found with id " + id);
		}

		Bookentity book = bookEntityRepository.findByBookId(reserve.getBookId());
		if (book == null) {
			throw new NotFoundException("Book not found with id " + reserve.getBookId());
		}

		Userentity student = userEntityRepository.findByStudentID(reserve.getStudentID());
		if (student == null) {
			throw new NotFoundException("Student not found with id " + reserve.getStudentID());
		}

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

		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setStatus("1");
		notificationEntity.setText("Your Request has been approved!");
		notificationEntity.setUser(student);

		try {
			bookEntityRepository.UpdateQuantityBook(book.getBookId());
			userEntityRepository.UpdateBorrowedStudentLimit(student.getStudentID());
			reserveEntityRepository.deleteById(reserve.getId());
			approveentityRepository.save(approveentity);
			notificationEntityRepository.save(notificationEntity);
			return new MessageResponse("Book approved successfully!");
		} catch (DataAccessException e) {
			log.error("Error while approving book with id " + id + ": " + e.getMessage(), e);
			throw new BookTransactionException("An error occurred while approving the book: " + e.getMessage(), e);
		}
	}

	// RECEIVED THE BOOK //

	public MessageResponse receiveTheBook(Long id) {
		try {
			Approveentity approveentity = approveentityRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Approveentity not found for id: " + id));

			Bookentity book = bookEntityRepository.findByBookId(approveentity.getBookId());
			Userentity student = userEntityRepository.findByStudentID(approveentity.getStudentID());

			// Update book and student information
			bookEntityRepository.UpdateQuantityBook(book.getBookId());
			userEntityRepository.UpdateBorrowedStudentLimit(student.getStudentID());

			// Create a ReceivedBook entity and save to the database
			ReceivedBook receivedBook = new ReceivedBook();
			receivedBook.setStatus("Borrowed");
			receivedBook.setDueDate(LocalDateTime.now().plusDays(3));
			receivedBook.setBookId(book.getBookId());
			receivedBook.setStudentID(student.getStudentID());
			receivedBookRepository.save(receivedBook);

			return new MessageResponse("Student received the book successfully!");

		} catch (DataAccessException e) {
			throw new BookTransactionException("An error occurred while accessing the database", e);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Approveentity not found for id: " + id, e);
		} catch (Exception e) {
			throw new BookTransactionException("An unexpected error occurred", e);
		}
	}

	// RETURN //
	// DUE DATA AND FINES //

	public MessageResponse ReturnAndCalculateFines(Long id) {
		try {

			// Custom HttpHeaders
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			// Find the Received by Id
			ReceivedBook received = receivedBookRepository.findByid(id);

			if (received == null) {
				throw new BookTransactionException("No received book found with id: " + id);
			}

			// Find the Book by BookId
			Bookentity book = bookEntityRepository.findByBookId(received.getBookId());
			if (book == null) {
				throw new NotFoundException("No book found with id: " + received.getBookId());
			}

			// Find the Student by StudentID
			Userentity student = userEntityRepository.findByStudentID(received.getStudentID());
			if (student == null) {
				throw new NotFoundException("No user found with student ID: " + received.getStudentID());
			}

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
				return new MessageResponse(message);
			} else {
				return new MessageResponse("Book returned successfully.");
			}
		} catch (NoSuchElementException e) {
			// Log the error or do something else with it
			return new MessageResponse("Resource not found.");
		} catch (Exception e) {
			// Log the error or do something else with it
			log.error("An error occurred: {}", e.getMessage());
			return new MessageResponse("An error occurred: " + e.getMessage());
		}

	}

	// SUCCESSFUL TRANSACTION //

	public MessageResponse successfulTransaction(Long id) {
		try {
			// Find the ReturnEntity by Id
			Returnentity returned = returnEntityRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("ReturnEntity with ID " + id + " not found."));

			// Find the Student by StudentID
			Userentity student = userEntityRepository.findByStudentID(returned.getStudentID());

			if (student == null) {
				throw new NotFoundException("Student with ID " + returned.getStudentID() + " not found.");
			}

			// Save the key data's of SuccessfulEntity
			Successfulentity successfulentity = new Successfulentity();
			successfulentity.setBookId(returned.getBookId());
			successfulentity.setCourse(student.getCourse());
			successfulentity.setDepartment(student.getDepartment());
			successfulentity.setDueDate(returned.getDueDate());
			successfulentity.setEmail(student.getEmail());
			successfulentity.setFirstname(student.getFirstname());
			successfulentity.setLastname(student.getLastname());
			successfulentity.setFines(returned.getFines());
			successfulentity.setReturnDate(returned.getReturnDate());
			successfulentity.setStudentID(student.getStudentID());

			successfulEntityRepository.save(successfulentity);
			returnEntityRepository.deleteById(id);

			return new MessageResponse("Book transaction successful.");

		} catch (NotFoundException e) {
			return new MessageResponse(e.getMessage());
		} catch (Exception e) {
			// Log the error or do something else with it
			log.error("An error occurred: {}", e.getMessage());
			return new MessageResponse("An error occurred: " + e.getMessage());
		}
	}

	// RESERVE THE BOOK //

	public MessageResponse ReserveBook(ReserveRequest reserveRequest) {
		try {
			Bookentity book = bookEntityRepository.findByBookId(reserveRequest.getBookId());
			if (book == null) {
				throw new NotFoundException("No book found with id: " + reserveRequest.getBookId());
			}

			Userentity student = userEntityRepository.findByStudentID(reserveRequest.getStudentID());
			if (student == null) {
				throw new NotFoundException("Student with ID " + reserveRequest.getStudentID() + " not found.");
			}

			// Save the Reservation Request //
			Reserveentity reserveentity = new Reserveentity();
			reserveentity.setBookId(book.getBookId());
			reserveentity.setBooktitle(book.getTitle());
			reserveentity.setCourse(student.getCourse());
			reserveentity.setDepartment(student.getDepartment());
			reserveentity.setEmail(student.getEmail());
			reserveentity.setFirstname(student.getFirstname());
			reserveentity.setLastname(student.getLastname());
			reserveentity.setStudentID(student.getStudentID());
			reserveentity.setStatus("Pending");

			reserveEntityRepository.save(reserveentity);

			return new MessageResponse("Book reservation successfully!");
		} catch (Exception e) {
			// Log the error or do something else with it
			log.error("An error occurred: {}", e.getMessage());
			return new MessageResponse("An error occurred: " + e.getMessage());
		}
	}

	// RESERVE THE BOOK //

	public MessageResponse BookLost(BookLostRequest bookLostRequest) {
		try {
			Bookentity book = bookEntityRepository.findByBookId(bookLostRequest.getBookId());
			if (book == null) {
				throw new NotFoundException("No book found with id: " + bookLostRequest.getBookId());
			}

			Userentity student = userEntityRepository.findByStudentID(bookLostRequest.getStudentID());
			if (student == null) {
				throw new NotFoundException("Student with ID " + bookLostRequest.getStudentID() + " not found.");
			}

			// Save the Book Lost //
			BookLostentity bookLostentity = new BookLostentity();
			bookLostentity.setBookAmount(bookLostRequest.getBookAmount());
			bookLostentity.setBookId(bookLostRequest.getBookId());
			bookLostentity.setCourse(bookLostRequest.getCourse());
			bookLostentity.setDepartment(bookLostRequest.getDepartment());
			bookLostentity.setEmail(bookLostRequest.getEmail());
			bookLostentity.setFirstname(bookLostRequest.getFirstname());
			bookLostentity.setLastname(bookLostRequest.getLastname());
			bookLostentity.setStatus(bookLostRequest.getStatus());
			bookLostentity.setStudentID(bookLostRequest.getStudentID());

			bookLostentityRepository.save(bookLostentity);

			return new MessageResponse("Book lost successfully save!");
		} catch (Exception e) {
			// Log the error or do something else with it
			log.error("An error occurred: {}", e.getMessage());
			return new MessageResponse("An error occurred: " + e.getMessage());
		}
	}

}
