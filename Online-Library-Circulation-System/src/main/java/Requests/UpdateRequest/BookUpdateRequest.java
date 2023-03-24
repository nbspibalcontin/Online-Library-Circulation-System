package Requests.UpdateRequest;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {

	private long id;

	private String bookId;

	@NotBlank(message = "Title should be valid")
	@Size(max = 30)
	private String title;

	@NotBlank(message = "Author should be valid")
	@Size(max = 30)
	private String author;

	@NotBlank(message = "Subject should be valid")
	@Size(max = 1000)
	private String subject;

	@NotBlank(message = "DatePublish should be valid")
	@Size(max = 20)
	private String datepublish;

	@Range(min = 1, max = 90)
	private int quantity;
}
