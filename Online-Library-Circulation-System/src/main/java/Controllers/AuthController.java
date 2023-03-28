package Controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

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
			Date expiration = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // Set expiration to 30 minutes from now
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(signInResponse.getToken());
		    headers.add(HttpHeaders.EXPIRES, expiration.toString());
			
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(signInResponse);
			
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
				throw new CreationException("Email is already taken!");
			}

			if (userEntityRepository.existsByStudentID(signUpRequest.getStudentID())) {
				throw new CreationException("Student ID is already taken! is already taken!");
			}

			MessageResponse messageResponse = addService.createUser(signUpRequest);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(messageResponse);

		} catch (CreationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

}
