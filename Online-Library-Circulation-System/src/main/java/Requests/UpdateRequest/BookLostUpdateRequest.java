package Requests.UpdateRequest;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookLostUpdateRequest {

	@NotBlank(message = "BookId should be valid")
	@Size(min = 2, max = 20, message = "BookId must be between {min} and {max} characters")
	private String bookId;

	@NotBlank(message = "StudentID should be valid")
	@Size(min = 2, max = 20, message = "StudentID must be between {min} and {max} characters")
	private String studentID;

	@NotBlank(message = "Firstname should be valid")
	@Size(min = 2, max = 20, message = "Firstname must be between {min} and {max} characters")
	private String firstname;

	@NotBlank(message = "Lastname should be valid")
	@Size(min = 2, max = 20, message = "Lastname must be between {min} and {max} characters")
	private String lastname;

	@NotBlank(message = "Department should be valid")
	@Size(min = 2, max = 20, message = "Department must be between {min} and {max} characters")
	private String department;

	@NotBlank(message = "Firstname should be valid")
	@Size(min = 2, max = 20, message = "Course must be between {min} and {max} characters")
	private String course;

	@NotBlank
	@Size(min = 2, max = 50, message = "Email must be between {min} and {max} characters")
	@Email(message = "Email should be valid")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@newbrighton.edu.ph$", message = "Email should belong to newbrighton.edu.ph domain")
	private String email;

	@NotBlank(message = "Status should be valid")
	@Size(min = 2, max = 20, message = "Course must be between {min} and {max} characters")
	private String status;
	
	@Range(min = 1, max = 100000, message = "Value must be between 1 and 100000")
	private int bookAmount;
}
