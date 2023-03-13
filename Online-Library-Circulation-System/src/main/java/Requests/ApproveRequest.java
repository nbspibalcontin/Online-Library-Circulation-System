package Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRequest {

		private Long bookId;
	    
		@NotBlank(message = "StudentID should be valid")
		private String StudentID;
		
		@NotBlank(message = "Firstname should be valid")
		private String firstname;
		
		@NotBlank(message = "Lastname should be valid")
		private String lastname;
		
		@NotBlank(message = "Department should be valid")
		private String department;
		
		@NotBlank(message = "Course should be valid")
		private String course;
		
		@NotBlank(message = "Email should be valid")
		@Email(message = "Email should be valid")
		private String email;
		
		@NotBlank(message = "BookTitle should be valid")
		private String booktitle;
	    
}
