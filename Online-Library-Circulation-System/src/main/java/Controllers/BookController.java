package Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Bookentity;
import Repositories.BookEntityRepository;
import Requests.SearchBook;

@RestController
@RequestMapping("/api/b")
public class BookController {

	@Autowired
	private BookEntityRepository bookEntityRepository;
	

	//	SEARCH BOOK	//
	
	@GetMapping("/searchbook")
	public ResponseEntity<?> searchCustomers(@RequestBody SearchBook searchBook) {
	    try {
	    	List<Bookentity> response = new ArrayList<>();
	        switch (searchBook.getFilter()) {
	        
	            case "title":
	            	response = bookEntityRepository.searchBytitle(searchBook.getKeyword());
	                break;
	                
	            case "author":
	            	response = bookEntityRepository.searchByauthor(searchBook.getKeyword());
	                break;
	                
	            case "subject":
	            	response = bookEntityRepository.searchBysubject(searchBook.getKeyword());
	                break;
	                
	            case "datepublish":
	            	response = bookEntityRepository.searchBydatepublish(searchBook.getKeyword());
	                break;
	                
	            default:
	            	response = bookEntityRepository.findAll();
	                break;
	        }
	        
	        if (response.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Book not found!");
	        } else {
	            return ResponseEntity.ok(response);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
}