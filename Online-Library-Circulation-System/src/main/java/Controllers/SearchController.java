package Controllers;

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
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.MessageResponse;
import Requests.SearchBook;
import Services.RetrieveServices.SearchService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
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
				throw new NotFoundException("Book not found!");
			} else {
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(entities);
			}
			
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

}