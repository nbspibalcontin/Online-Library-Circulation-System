package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Entities.Bookentity;
import Entities.Reserveentity;
import Entities.Userentity;
import Repositories.BookEntityRepository;
import Repositories.ReserveEntityRepository;
import Repositories.UserEntityRepository;
import Requests.ReserveRequest;

@Service
public class UserService {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ADD USER //
	public ResponseEntity<?> createUser(Userentity userentity) {
		try {

			userentity.setPassword(passwordEncoder.encode(userentity.getPassword()));
			userEntityRepository.save(userentity);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("User registered successfully!");

		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// RESERVE THE BOOK //
	public ResponseEntity<?> ReserveBook(ReserveRequest reserveRequest) {
		try {

			Bookentity book = bookEntityRepository.findBybookId(reserveRequest.getBookId());

			Userentity student = userEntityRepository.findByStudentID(reserveRequest.getStudentID());

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

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");

			return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body("Book reservation successfully!");

		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}

	}

}
