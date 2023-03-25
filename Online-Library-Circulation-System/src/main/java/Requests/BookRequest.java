package Requests;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

	@NotBlank(message = "BookId should be valid")
	@Size(min = 2, max = 20, message = "BookId must be between {min} and {max} characters")
	private String bookId;

	@NotBlank(message = "Title should be valid")
	@Size(min = 2, max = 20, message = "Title must be between {min} and {max} characters")
	private String title;

	@NotBlank(message = "Author should be valid")
	@Size(min = 2, max = 20, message = "Author must be between {min} and {max} characters")
	private String author;

	@NotBlank(message = "Subject should be valid")
	@Size(min = 2, max = 1000, message = "Subject must be between {min} and {max} characters")
	private String subject;

	@NotBlank(message = "DatePublish should be valid")
	@Size(min = 2, max = 20, message = "Firstname must be between {min} and {max} characters")
	private String datepublish;

	@Range(min = 1, max = 90, message = "Value must be between 1 and 90")
	private int quantity;
}
