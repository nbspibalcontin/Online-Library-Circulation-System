package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.BookEntity;
import Repositories.BookEntityRepository;
import Requests.SearchBook;

@RestController
@RequestMapping("/api/b")
public class BookController {

	@Autowired
	private BookEntityRepository bookEntityRepository;
	

	//	SEARCH BOOK	//
	
	@GetMapping("/searchbook")
	public ResponseEntity<List<BookEntity>> searchCustomers(@RequestBody SearchBook searchBook) {
	    try {
	        List<BookEntity> response = null;
	        switch (searchBook.getFilter()) {
	        
	            case "title":
	            	response = bookEntityRepository.findByTitle(searchBook.getKeyword());
	                break;
	                
	            case "author":
	            	response = bookEntityRepository.findByAuthor(searchBook.getKeyword());
	                break;
	                
	            case "subject":
	            	response = bookEntityRepository.findBySubject(searchBook.getKeyword());
	                break;
	                
	            case "datepublish":
	            	response = bookEntityRepository.findByDatepublish(searchBook.getKeyword());
	                break;
	                
	            default:
	            	response = bookEntityRepository.findAll();
	                break;
	        }
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
