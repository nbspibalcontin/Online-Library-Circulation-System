package Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Userentity;
import Reponses.ErrorResponse;
import Reponses.MessageResponse;
import Repositories.UserEntityRepository;
import Requests.SignInRequest;
import Services.AuthService;
import Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> SignIn(@Valid @RequestBody SignInRequest signInRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors));
		}

		return authService.SignInAuth(signInRequest);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> SignUp(@Valid @RequestBody Userentity userentity, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(new ErrorResponse(errors));
		}

		if (userEntityRepository.existsByEmail(userentity.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		if (userEntityRepository.existsByStudentID(userentity.getStudentID())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Student_ID is already taken!"));
		}

		return userService.createUser(userentity);

	}

}
