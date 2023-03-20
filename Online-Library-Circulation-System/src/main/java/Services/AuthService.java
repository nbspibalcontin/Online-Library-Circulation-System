package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Entities.Userentity;
import Reponses.JwtResponse;
import Reponses.MessageResponse;
import Repositories.UserEntityFindByEmail;
import Requests.SignInRequest;

@Service
public class AuthService {

	@Autowired
	private UserEntityFindByEmail userEntityFindByEmail;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public ResponseEntity<?> SignInAuth(SignInRequest signInRequest) {
		try {
			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
				if (authentication.isAuthenticated()) {

					String jwt = jwtService.generateToken(signInRequest.getEmail());

					final Userentity user = userEntityFindByEmail.findByEmail(signInRequest.getEmail());

					return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getStudentID(),
							user.getFirstname(), user.getLastname(), user.getDepartment(), user.getCourse(),
							user.getEmail(), user.getRoles()));

				} else {
					throw new UsernameNotFoundException("Error: Invalid user request !");
				}
			} catch (AuthenticationException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new MessageResponse("Error: Invalid Email or Password"));
			}
		} catch (Exception e) {
			// Log the error or do something else with it
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}

	}
}
