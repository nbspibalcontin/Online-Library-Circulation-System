package Requests.UpdateRequest;

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
public class ApproveUpdateRequest {

	private long id;

	@NotBlank(message = "BookId should be valid")
	@Size(max = 30)
	private String bookId;

	@NotBlank(message = "StudentID should be valid")
	@Size(max = 30)
	private String studentID;

	@NotBlank(message = "Firstname should be valid")
	@Size(max = 30)
	private String firstname;

	@NotBlank(message = "Lastname should be valid")
	@Size(max = 30)
	private String lastname;

	@NotBlank(message = "Department should be valid")
	@Size(max = 30)
	private String department;

	@NotBlank(message = "Course should be valid")
	@Size(max = 30)
	private String course;

	@NotBlank
	@Size(min = 2, max = 50, message = "Email must be between {min} and {max} characters")
	@Email(message = "Email should be valid")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@newbrighton.edu.ph$", message = "Email should belong to newbrighton.edu.ph domain")
	private String email;

	@NotBlank(message = "BookTitle should be valid")
	@Size(max = 30)
	private String booktitle;
}
