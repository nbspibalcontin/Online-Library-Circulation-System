package Services.RetrieveServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class FindAllService {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ApproveentityRepository approveentityRepository;

	@Autowired
	private ReceivedBookRepository receivedBookRepository;

	@Autowired
	private ReturnEntityRepository returnEntityRepository;

	@Autowired
	private SuccessfulEntityRepository successfulEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	// ------------------------FindAll---------------------------//

	// GET ALL SUCCESSFUL TRANSACTION //

	public List<Successfulentity> getAllSuccessfulTransaction() {
		try {
			return successfulEntityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching successful transactions: " + e.getMessage(),
					e);
		}
	}

	// GET ALL BOOKS //

	public List<Bookentity> getAllBooks() {
		try {
			return bookEntityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching books: " + e.getMessage(), e);
		}
	}

	// GET ALL APPROVAL REQUEST //

	public List<Approveentity> getAllApprovalRequest() {
		try {
			return approveentityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching approve request: " + e.getMessage(), e);
		}
	}

	// GET ALL RECEIVED BOOKS //

	public List<ReceivedBook> getAllReceivedBooks() {
		try {
			return receivedBookRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching books received: " + e.getMessage(), e);
		}
	}

	// GET ALL RETURNED BOOKS //

	public List<Returnentity> getAllReturnedBooks() {
		try {
			return returnEntityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching returned books: " + e.getMessage(), e);
		}
	}

	// GET ALL RESERVATION REQUEST //

	public List<Reserveentity> getAllReservation() {
		try {
			return reserveEntityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching reservations: " + e.getMessage(), e);
		}
	}

	// GET ALL RETURNED BOOKS //

	public List<Userentity> getAllUsers() {
		try {
			return userEntityRepository.findAll();
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching users: " + e.getMessage(), e);
		}
	}

}
