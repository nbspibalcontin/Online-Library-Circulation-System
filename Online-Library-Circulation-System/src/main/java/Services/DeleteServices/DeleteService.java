package Services.DeleteServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.BookLostentityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;

@Service
public class DeleteService {

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
	private SuccessfulEntityRepository successfulEntityRepository;
	
	@Autowired
	private BookLostentityRepository bookLostentityRepository;

	// Delete Book

	public MessageResponse DeleteBook(Long id) {

		bookEntityRepository.deleteById(id);

		return new MessageResponse("Book successfully deleted.");

	}

	// Delete Reservation

	public MessageResponse DeleteReserve(Long id) {

		reserveEntityRepository.deleteById(id);

		return new MessageResponse("Reservation request successfully deleted.");

	}
	
	// Delete Book Lost

	public MessageResponse DeleteBookLost(Long id) {

		bookLostentityRepository.deleteById(id);

		return new MessageResponse("Book Lost successfully deleted.");

	}

	// Delete User

	public MessageResponse DeleteUser(Long id) {

		userEntityRepository.deleteById(id);

		return new MessageResponse("User successfully deleted.");

	}

	// Delete Approve

	public MessageResponse DeleteApprove(Long id) {

		approveentityRepository.deleteById(id);

		return new MessageResponse("Approve request successfully deleted.");

	}

	// Delete Received

	public MessageResponse DeleteReceived(Long id) {

		receivedBookRepository.deleteById(id);

		return new MessageResponse("Received book successfully deleted.");

	}

	// Delete Return

	public MessageResponse DeleteReturn(Long id) {

		returnEntityRepository.deleteById(id);

		return new MessageResponse("Return book successfully deleted.");

	}

	// Delete SucecssTransaction

	public MessageResponse DeleteSucecssTransaction(Long id) {

		successfulEntityRepository.deleteById(id);

		return new MessageResponse("Successful transaction successfully deleted.");

	}
}
