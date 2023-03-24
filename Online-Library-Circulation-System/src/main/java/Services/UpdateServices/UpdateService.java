package Services.UpdateServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Entities.Approveentity;
import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Successfulentity;
import Entities.Userentity;
import ExeptionHandler.InvalidDataException;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;
import Requests.UpdateRequest.ApproveUpdateRequest;
import Requests.UpdateRequest.BookUpdateRequest;
import Requests.UpdateRequest.ReceivedUpdateRequest;
import Requests.UpdateRequest.ReserveUpdateRequest;
import Requests.UpdateRequest.ReturnUpdateRequest;
import Requests.UpdateRequest.SuccessfulTransactionUpdateRequest;
import Requests.UpdateRequest.UserUpdateRequest;

@Service
public class UpdateService {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ApproveentityRepository approveentityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private ReceivedBookRepository receivedBookRepository;

	@Autowired
	private ReturnEntityRepository returnEntityRepository;

	@Autowired
	private SuccessfulEntityRepository successfulEntityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ------------------------UPDATE---------------------------//

	// UPDATE BOOK //

	public MessageResponse updateBook(Long id, BookUpdateRequest updateRequest) {

		Bookentity bookentity = bookEntityRepository.findByid(id);
		if (bookentity == null) {
			throw new NotFoundException("Book not found with id " + id);
		}

		if (updateRequest.getBookId() == null || updateRequest.getAuthor() == null
				|| updateRequest.getDatepublish() == null || updateRequest.getQuantity() == 0
				|| updateRequest.getSubject() == null || updateRequest.getTitle() == null) {
			throw new InvalidDataException("Update request contains null fields");
		}

		bookentity.setId(id);
		bookentity.setBookId(updateRequest.getBookId());
		bookentity.setDatepublish(updateRequest.getDatepublish());
		bookentity.setAuthor(updateRequest.getAuthor());
		bookentity.setTitle(updateRequest.getTitle());
		bookentity.setQuantity(updateRequest.getQuantity());
		bookentity.setSubject(updateRequest.getSubject());

		try {
			bookEntityRepository.save(bookentity);

			return new MessageResponse("Book successfully updated.");

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving book entity", e);
		}
	}

	// UPDATE RESERVATION //

	public MessageResponse UpdateReserve(Long id, ReserveUpdateRequest reserveUpdateRequest) {

		Reserveentity reserveentity = reserveEntityRepository.findByid(id);

		if (reserveentity == null) {
			throw new NotFoundException("Reserve not found with id " + id);
		}

		if (reserveUpdateRequest.getBookId() == null || reserveUpdateRequest.getBooktitle() == null
				|| reserveUpdateRequest.getCourse() == null || reserveUpdateRequest.getDepartment() == null
				|| reserveUpdateRequest.getEmail() == null || reserveUpdateRequest.getFirstname() == null
				|| reserveUpdateRequest.getLastname() == null) {
			throw new InvalidDataException("Update request contains null fields");
		}

		reserveentity.setId(id);
		reserveentity.setBookId(reserveUpdateRequest.getBookId());
		reserveentity.setBooktitle(reserveUpdateRequest.getBooktitle());
		reserveentity.setCourse(reserveUpdateRequest.getCourse());
		reserveentity.setDepartment(reserveUpdateRequest.getDepartment());
		reserveentity.setEmail(reserveUpdateRequest.getFirstname());
		reserveentity.setFirstname(reserveUpdateRequest.getFirstname());
		reserveentity.setLastname(reserveUpdateRequest.getLastname());
		reserveentity.setReserveAt(reserveUpdateRequest.getReserveAt());
		reserveentity.setStatus(reserveentity.getStatus());
		reserveentity.setStudentID(reserveentity.getStudentID());

		try {
			reserveEntityRepository.save(reserveentity);
			return new MessageResponse("Reservation successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving reserve entity", e);
		}

	}

	// UPDATE APPROVE //

	public MessageResponse UpdateApprove(Long id, ApproveUpdateRequest updateRequest) {

		Approveentity approveentity = approveentityRepository.findByid(id);

		if (approveentity == null) {
			throw new NotFoundException("Approve book not found with id " + id);
		}

		if (updateRequest.getBookId() == null || updateRequest.getBooktitle() == null
				|| updateRequest.getCourse() == null || updateRequest.getDepartment() == null
				|| updateRequest.getEmail() == null || updateRequest.getFirstname() == null
				|| updateRequest.getLastname() == null || updateRequest.getStudentID() == null
				|| updateRequest.getBookId() == null) {
			throw new InvalidDataException("Update request contains null fields");
		}

		approveentity.setId(id);
		approveentity.setApprovedAt(approveentity.getApprovedAt());
		approveentity.setBookId(updateRequest.getBookId());
		approveentity.setBooktitle(updateRequest.getBooktitle());
		approveentity.setCourse(updateRequest.getCourse());
		approveentity.setDepartment(updateRequest.getDepartment());
		approveentity.setEmail(updateRequest.getEmail());
		approveentity.setFirstname(updateRequest.getFirstname());
		approveentity.setLastname(updateRequest.getLastname());
		approveentity.setStatus(approveentity.getStatus());
		approveentity.setStudentID(updateRequest.getStudentID());

		try {
			approveentityRepository.save(approveentity);
			return new MessageResponse("Approve book successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving approve entity", e);
		}

	}

	// UPDATE RECEIEVED BOOK //

	public MessageResponse UpdateReceivedBook(Long id, ReceivedUpdateRequest updateRequest) {

		ReceivedBook receivedBook = receivedBookRepository.findByid(id);

		if (receivedBook == null) {
			throw new NotFoundException("Received book not found with id " + id);
		}

		if (updateRequest.getBookId() == null || updateRequest.getStudentID() == null) {
			throw new InvalidDataException("Update request contains null fields");
		}

		receivedBook.setId(id);
		receivedBook.setBookId(updateRequest.getBookId());
		receivedBook.setStatus(receivedBook.getStatus());
		receivedBook.setDueDate(receivedBook.getDueDate());
		receivedBook.setStudentID(updateRequest.getStudentID());

		try {
			receivedBookRepository.save(receivedBook);
			return new MessageResponse("Received book successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving received entity", e);
		}

	}

	// UPDATE RETURN BOOK //

	public MessageResponse UpdateReturnedBook(Long id, ReturnUpdateRequest updateRequest) {

		Returnentity returnentity = returnEntityRepository.findByid(id);

		if (returnentity == null) {
			throw new NotFoundException("Returned book not found with id " + id);
		}

		if (updateRequest.getBookId() == null || updateRequest.getStudentID() == null
				|| updateRequest.getFines() == 0) {
			throw new InvalidDataException("Update request contains null fields");
		}

		returnentity.setId(id);
		returnentity.setBookId(updateRequest.getBookId());
		returnentity.setDueDate(updateRequest.getDueDate());
		returnentity.setReturnDate(updateRequest.getReturnDate());
		returnentity.setFines(updateRequest.getFines());
		returnentity.setStatus(returnentity.getStatus());
		returnentity.setStudentID(updateRequest.getStudentID());

		try {
			returnEntityRepository.save(returnentity);
			return new MessageResponse("Return book successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving return entity", e);
		}

	}

	// UPDATE SUCCESSFUL TRANSACTION //

	public MessageResponse UpdateSuccessfualTransaction(Long id, SuccessfulTransactionUpdateRequest updateRequest) {

		Successfulentity successfulentity = successfulEntityRepository.findByid(id);

		if (successfulentity == null) {
			throw new NotFoundException("Successful Transaction not found with id " + id);
		}

		if (updateRequest.getBookId() == null || updateRequest.getDueDate() == null || updateRequest.getCourse() == null
				|| updateRequest.getDepartment() == null || updateRequest.getEmail() == null
				|| updateRequest.getFirstname() == null || updateRequest.getLastname() == null
				|| updateRequest.getStudentID() == null || updateRequest.getBookId() == null
				|| updateRequest.getReturnDate() == null || updateRequest.getFines() == 0) {
			throw new InvalidDataException("Update request contains null fields");
		}

		successfulentity.setId(id);
		successfulentity.setBookId(updateRequest.getBookId());
		successfulentity.setCourse(updateRequest.getCourse());
		successfulentity.setDepartment(updateRequest.getDepartment());
		successfulentity.setFirstname(updateRequest.getFirstname());
		successfulentity.setLastname(updateRequest.getLastname());
		successfulentity.setEmail(updateRequest.getEmail());
		successfulentity.setDueDate(updateRequest.getDueDate());
		successfulentity.setReturnDate(updateRequest.getReturnDate());
		successfulentity.setFines(updateRequest.getFines());
		successfulentity.setStudentID(updateRequest.getStudentID());

		try {
			successfulEntityRepository.save(successfulentity);
			return new MessageResponse("Transaction successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving success transaction entity", e);
		}

	}

	// UPDATE USER //

	public MessageResponse UpdateUser(Long id, UserUpdateRequest updateRequest) {

		Userentity userentity = userEntityRepository.findByid(id);

		if (userentity == null) {
			throw new NotFoundException("User not found with id " + id);
		}

		if (updateRequest.getPassword() == null || updateRequest.getCourse() == null
				|| updateRequest.getDepartment() == null || updateRequest.getEmail() == null
				|| updateRequest.getFirstname() == null || updateRequest.getLastname() == null
				|| updateRequest.getStudentID() == null) {
			throw new InvalidDataException("Update request contains null fields");
		}

		userentity.setId(id);
		userentity.setCourse(updateRequest.getCourse());
		userentity.setDepartment(updateRequest.getDepartment());
		userentity.setEmail(updateRequest.getEmail());
		userentity.setFirstname(updateRequest.getFirstname());
		userentity.setLastname(updateRequest.getLastname());
		userentity.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

		try {
			userEntityRepository.save(userentity);
			return new MessageResponse("User successfully updated.");
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data integrity violation while saving user entity", e);
		}

	}
}
