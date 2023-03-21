package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Requests.SearchBook;
import Services.BookService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	private BookService bookService;

	// SEARCH BOOK //

	@GetMapping("/searchbook")
	public ResponseEntity<?> searchCustomers(@RequestBody SearchBook searchBook) {
		return bookService.SearchBook(searchBook);

	}

}