package Services.RetrieveServices;

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
public class FindByIdService {

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
	private UserEntityRepository userEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	// ------------------------FindBy---------------------------//

	// GET SUCCESSFUL TRANSACTION BY ID //

	public Successfulentity getSuccessfulTransactionById(Long id) {
		try {
			return successfulEntityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching successful transactions: " + e.getMessage(),
					e);
		}
	}

	// GET APPROVAL REQUEST BY ID //

	public Approveentity getApprovalRequestById(Long id) {
		try {
			return approveentityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching approve request: " + e.getMessage(), e);
		}
	}

	// GET RECEIVED BOOKS BY ID //

	public ReceivedBook getReceivedBooksById(Long id) {
		try {
			return receivedBookRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching books received: " + e.getMessage(), e);
		}
	}

	// GET BOOKS BY ID //

	public Bookentity getBooksById(Long id) {
		try {
			return bookEntityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching books: " + e.getMessage(), e);
		}
	}

	// GET RETURNED BOOKS BY ID //

	public Returnentity getReturnedBooksById(Long id) {
		try {
			return returnEntityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching returned books: " + e.getMessage(), e);
		}
	}

	// GET STUDENT BY ID //

	public Userentity getStudentById(Long id) {
		try {
			return userEntityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching reservations: " + e.getMessage(), e);
		}
	}

	// GET STUDENT BY ID //

	public Reserveentity getReserveById(Long id) {
		try {
			return reserveEntityRepository.findByid(id);
		} catch (Exception e) {
			// Log the error or do something else with it
			throw new RuntimeException("An error occurred while fetching users: " + e.getMessage(), e);
		}
	}
}
