package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Bookentity;
import Entities.Reserveentity;
import Reponses.MessageResponse;
import Repositories.BookEntityRepository;
import Repositories.ReserveEntityRepository;
import Repositories.UserEntityRepository;
import Requests.ReserveRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private BookEntityRepository bookEntityRepository;
	
	@Autowired
	private ReserveEntityRepository reserveEntityRepository;
	
    @Autowired
    private UserEntityRepository userEntityRepository;

//	@PostMapping("/url")
//	public ResponseEntity<?> create(@RequestBody Dto dto) {
//		try {
//			//TODO Implement Your Logic To Save Data And Return Result Through ResponseEntity
//			return new ResponseEntity<>("Create Result", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	//-----ADMIN----//
		
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> Adminpage() {
		try {
			return new ResponseEntity<>("Welcome to Admin page", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//	ADD BOOK	//
	
	@PostMapping("/b/addbook")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> SaveBook(@Valid @RequestBody Bookentity bookEntity, BindingResult bindingResult) {
		try {
			
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.badRequest().body("Error: Invalid Book form data");
	        }
	        
	        if (bookEntity == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	        
			bookEntityRepository.save(bookEntity);
			
			return new ResponseEntity<>("Book created successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//-----USER----//
		
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> Userpage() {
		try {
			return new ResponseEntity<>("Welcome to User page", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//-----RESERVE BOOK----//
	
	@PostMapping("/b/reserve")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> ReserveBook(@Valid @RequestBody ReserveRequest reserveRequest,Bookentity bookentity, BindingResult bindingResult) {
		try {
			
			Bookentity book = bookEntityRepository.findByTitle(reserveRequest.getBooktitle());
			
	        if (book.getQuantity() == 0) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Currently, the book is not available."));
	        }
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.badRequest().body("Error: Invalid Reserve form data");
	        }
	        
	        // Check if the book is already reserved by the same student
	        Reserveentity existingReservation = reserveEntityRepository.findByBooktitleAndStudentID(reserveRequest.getBooktitle(), reserveRequest.getStudentID());
	        if (existingReservation != null) {
	            // Book is already reserved by the same student, return a 409 Conflict response
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("You already reserved this book"));
	        }
	        
		    if (userEntityRepository.existsByEmail(reserveRequest.getEmail())) {
		        Reserveentity reserveentity = new Reserveentity();
		        reserveentity.setBooktitle(reserveRequest.getBooktitle());
		        reserveentity.setCourse(reserveRequest.getCourse());
		        reserveentity.setDepartment(reserveRequest.getDepartment());
		        reserveentity.setEmail(reserveRequest.getEmail());
		        reserveentity.setFirstname(reserveRequest.getFirstname());
		        reserveentity.setLastname(reserveRequest.getLastname());
		        reserveentity.setStudentID(reserveRequest.getStudentID());
		        reserveentity.setStatus("Pending");	      	        
		        
		        reserveEntityRepository.save(reserveentity);
		        
				return new ResponseEntity<>("Book reservation successfully!", HttpStatus.OK);
	       }else {
	    	   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	       }
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
