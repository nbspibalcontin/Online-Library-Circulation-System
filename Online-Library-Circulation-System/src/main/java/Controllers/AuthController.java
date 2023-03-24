package Controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ExeptionHandler.CreationException;
import Reponses.ErrorResponse;
import Reponses.MessageResponse;
import Reponses.SignInResponse;
import Repositories.UserEntityRepository;
import Requests.SignInRequest;
import Requests.SignUpRequest;
import Services.AuthService;
import Services.CreateServices.AddService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private AddService addService;

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String error = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ErrorResponse(Collections.singletonList(error)));
		}
		try {
			SignInResponse signInResponse = authService.signInAuth(signInRequest);
			return ResponseEntity.ok(signInResponse);
		} catch (AuthenticationServiceException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse(Collections.singletonList(e.getMessage())));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> SignUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer token");
			
			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			if (userEntityRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
			}

			if (userEntityRepository.existsByStudentID(signUpRequest.getStudentID())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Student_ID is already taken!"));
			}

			MessageResponse messageResponse = addService.createUser(signUpRequest);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (CreationException ex) {
			return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
		} catch (DataAccessException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(Collections.singletonList("Error creating user")));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(Collections.singletonList(ex.getMessage())));
		}
	}

}
