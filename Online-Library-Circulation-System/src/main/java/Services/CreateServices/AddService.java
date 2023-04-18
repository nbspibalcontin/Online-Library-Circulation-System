package Services.CreateServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Entities.Bookentity;
import Entities.Userentity;
import ExeptionHandler.CreationException;
import Reponses.MessageResponse;
import Repositories.BookEntityRepository;
import Repositories.UserEntityRepository;
import Requests.BookRequest;
import Requests.SignUpRequest;

@Service
public class AddService {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ADD BOOK //

	public MessageResponse createBook(BookRequest bookRequest) {
		try {
			Bookentity bookentity = new Bookentity();
			bookentity.setAuthor(bookRequest.getAuthor());
			bookentity.setBookId(bookRequest.getBookId());
			bookentity.setDatepublish(bookRequest.getDatepublish());
			bookentity.setQuantity(bookRequest.getQuantity());
			bookentity.setSubject(bookRequest.getSubject());
			bookentity.setTitle(bookRequest.getTitle());

			bookEntityRepository.save(bookentity);

			return new MessageResponse("Book created successfully!");

		} catch (RuntimeException e) {
			throw new CreationException("Error creating book", e);
		}

	}

	// ADD USER //

	public MessageResponse createUser(SignUpRequest signUpRequest) {
		try {
			Userentity userentity = new Userentity();
			userentity.setCourse(signUpRequest.getCourse());
			userentity.setDepartment(signUpRequest.getDepartment());
			userentity.setEmail(signUpRequest.getEmail());
			userentity.setLastname(signUpRequest.getLastname());
			userentity.setStudentID(signUpRequest.getStudentID());
			userentity.setFirstname(signUpRequest.getFirstname());
			userentity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

			userEntityRepository.save(userentity);

			return new MessageResponse("User registered successfully!");

		} catch (RuntimeException e) {
			throw new CreationException("Error creating user", e);
		}
	}

}
