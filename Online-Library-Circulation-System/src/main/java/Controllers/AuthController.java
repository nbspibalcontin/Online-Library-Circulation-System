package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import Entities.UserEntity;
import Reponses.MessageResponse;
import Repositories.UserEntityRepository;
import Requests.SignInRequest;
import Services.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserEntityRepository userEntityRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
	@PostMapping("/signin")
	public ResponseEntity<?> create(@RequestBody SignInRequest signInRequest) {
		try {
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
	        if (authentication.isAuthenticated()) {
	        	return new ResponseEntity<>(jwtService.generateToken(signInRequest.getEmail()), HttpStatus.OK);
	        } else {
	            throw new UsernameNotFoundException("invalid user request !");
	        }			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> create(@RequestBody UserEntity userEntity) {
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
