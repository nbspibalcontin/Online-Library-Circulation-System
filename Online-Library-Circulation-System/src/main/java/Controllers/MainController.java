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
import Repositories.BookEntityRepository;

@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private BookEntityRepository bookEntityRepository;

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
	public ResponseEntity<?> SaveBook(@RequestBody Bookentity bookEntity, BindingResult bindingResult) {
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
			//TODO Implement Your Logic To Save Data And Return Result Through ResponseEntity
			return new ResponseEntity<>("Welcome to User page", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
