package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Approveentity;
import Entities.Bookentity;
import Entities.Reserveentity;
import Entities.Userentity;
import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.ReserveEntityRepository;
import Repositories.UserEntityRepository;
import Requests.ApproveRequest;
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
    
    @Autowired
    private ApproveentityRepository approveentityRepository;
    

//	@PostMapping("/url")
//	public ResponseEntity<?> create(@RequestBody Dto dto) {
//		try {
//			//TODO Implement Your Logic To Save Data And Return Result Through ResponseEntity
//			return new ResponseEntity<>("Create Result", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
  //------------------------ADMIN---------------------------//
		
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
	
	@PostMapping("/addbook")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> SaveBook(@Valid @RequestBody Bookentity bookEntity, BindingResult bindingResult) {
		try {
			
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Invalid Reserve form data"));
	        }
	        
	        if (bookEntity == null) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Can't be empty"));
	        }
	        
			bookEntityRepository.save(bookEntity);
			
			return new ResponseEntity<>("Book created successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//-----APPROVE THE RESERVATION OF BOOK----//
	
	@PostMapping("/approve/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> ApproveTheRequest(@Valid @PathVariable Long id,ApproveRequest approveRequest, BindingResult bindingResult) {
		try {
			try {
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Invalid Reserve form data"));
	        }
	        
			Reserveentity reserve = reserveEntityRepository.findByid(id);

			        Approveentity approveentity = new Approveentity();
			        approveentity.setBooktitle(reserve.getBooktitle());
			        approveentity.setCourse(reserve.getCourse());
			        approveentity.setDepartment(reserve.getDepartment());
			        approveentity.setEmail(reserve.getEmail());
			        approveentity.setFirstname(reserve.getFirstname());
			        approveentity.setLastname(reserve.getLastname());
			        approveentity.setStudentID(reserve.getStudentID());
			        approveentity.setStatus("Approved");	      	        
			        
			        approveentityRepository.save(approveentity);
			        
			        reserveEntityRepository.deleteById(reserve.getId());
					
			        
					return new ResponseEntity<>("Book approved successfully!", HttpStatus.OK);
					
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//------------------------USER---------------------------//
		
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
	
	@PostMapping("/reserve")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> ReserveBook(@Valid @RequestBody ReserveRequest reserveRequest,Bookentity bookentity, BindingResult bindingResult) {
		try {
			try {
				
			Bookentity book = bookEntityRepository.findBybookId(reserveRequest.getBookId());
			
			Userentity student = userEntityRepository.findByStudentID(reserveRequest.getStudentID());
			
        	
        	
	        if (book.getQuantity() == 0) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Currently, the book is not available."));
	        }
	        
	        if (bindingResult.hasErrors()) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error: Invalid Reserve form data"));
	        }
	        
	        // check if the student has already borrowed 3 books
	        if (student.getBooksBorrowed() >= 3) {
	        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Student has already borrowed 3 books"));
	        }
	        	        
	        // Check if the book is already reserved by the same student
	        Reserveentity existingReservation = reserveEntityRepository.findByBookIdAndStudentID(reserveRequest.getBookId(), reserveRequest.getStudentID());
	        if (existingReservation != null) {
	            // Book is already reserved by the same student, return a 409 Conflict response
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("You already reserved this book"));
	        }
	        		book.setQuantity(book.getQuantity() - 1);
	        		student.setBooksBorrowed(student.getBooksBorrowed() + 1);
        	
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
    		        
    				return new ResponseEntity<>("Book reservation successfully!", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
