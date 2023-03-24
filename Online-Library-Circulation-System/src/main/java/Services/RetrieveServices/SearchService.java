package Services.RetrieveServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Entities.Bookentity;
import Repositories.BookEntityRepository;
import Requests.SearchBook;

@Service
public class SearchService {

	@Autowired
	BookEntityRepository bookEntityRepository;

	// SEARCH BOOK //

	public List<Bookentity> searchBook(SearchBook searchBook) {
		if (searchBook == null) {
			throw new IllegalArgumentException("Search book cannot be null");
		}
		if (searchBook.getFilter() == null) {
			throw new IllegalArgumentException("Search filter cannot be null");
		}
		if (searchBook.getKeyword() == null) {
			throw new IllegalArgumentException("Search keyword cannot be null");
		}

		switch (searchBook.getFilter()) {
		case "title":
			return bookEntityRepository.searchBytitle(searchBook.getKeyword());

		case "author":
			return bookEntityRepository.searchByauthor(searchBook.getKeyword());

		case "subject":
			return bookEntityRepository.searchBysubject(searchBook.getKeyword());

		case "datepublish":
			return bookEntityRepository.searchBydatepublish(searchBook.getKeyword());

		default:
			throw new IllegalArgumentException("Invalid search criteria: " + searchBook.getFilter());
		}
	}

}
