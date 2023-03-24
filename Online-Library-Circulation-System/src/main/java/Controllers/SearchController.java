package Controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Bookentity;
import Reponses.ErrorResponse;
import Requests.SearchBook;
import Services.RetrieveServices.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	// SEARCH BOOK //

	@GetMapping("/searchbook")
	public ResponseEntity<?> search(@RequestBody SearchBook searchBook) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "No Bearer token");

		try {
			List<Bookentity> entities = searchService.searchBook(searchBook);

			if (entities.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("Book not found!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(entities);
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(Collections.singletonList(e.getMessage())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(Collections.singletonList("An unexpected error occurred")));
		}
	}

}