package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Entities.Userentity;
import Reponses.SignInResponse;
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

	public SignInResponse signInAuth(SignInRequest signInRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				String jwt = jwtService.generateToken(signInRequest.getEmail());
				final Userentity user = userEntityFindByEmail.findByEmail(signInRequest.getEmail());
				return new SignInResponse(jwt, user.getId(), user.getStudentID(), user.getFirstname(),
						user.getLastname(), user.getDepartment(), user.getCourse(), user.getEmail(), user.getRoles());
			} else {
				throw new UsernameNotFoundException("Invalid user request");
			}
		} catch (Exception e) {
			throw new AuthenticationServiceException("Invalid email or password");
		}
	}
}
