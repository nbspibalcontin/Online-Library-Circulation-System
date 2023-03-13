package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Userentity;
import Reponses.JwtResponse;
import Reponses.MessageResponse;
import Repositories.UserEntityFindByEmail;
import Repositories.UserEntityRepository;
import Requests.SignInRequest;
import Services.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserEntityRepository userEntityRepository;
    
    @Autowired
    private UserEntityFindByEmail userEntityFindByEmail;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
	@PostMapping("/signin")
	public ResponseEntity<?> SignIn(@Valid @RequestBody SignInRequest signInRequest, BindingResult bindingResult) {
		try {
			try {
				
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Invalid Reserve form data"));
	        }
	        
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
	        if (authentication.isAuthenticated()) {
	        	
		        
	        	String jwt = jwtService.generateToken(signInRequest.getEmail());
	        	
	        	final Userentity user = userEntityFindByEmail.findByEmail(signInRequest.getEmail());
	        		       	        	
	        	return ResponseEntity.ok(new JwtResponse(jwt,
	        			user.getId(),
	        			user.getStudentID(),
	        			user.getFirstname(),
	        			user.getLastname(),
	        			user.getDepartment(),
	        			user.getCourse(),
	        			user.getEmail(),
	        			user.getRoles()));
	        	
	        } else {
	            throw new UsernameNotFoundException("Error: Invalid user request !");
	        }	
			}catch (AuthenticationException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: Invalid username or password"));
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> SignUp(@Valid @RequestBody Userentity userEntity, BindingResult bindingResult) {
		try {
		    if (userEntityRepository.existsByEmail(userEntity.getEmail())) {
		        return ResponseEntity
		            .badRequest()
		            .body(new MessageResponse("Error: Email is already taken!"));
		      }		 
		    
		    if (userEntityRepository.existsByStudentID(userEntity.getStudentID())) {
		        return ResponseEntity
		            .badRequest()
		            .body(new MessageResponse("Error: Student_ID is already taken!"));
		      }	
		    
	    	userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
	    	userEntityRepository.save(userEntity);
	    	
			return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
