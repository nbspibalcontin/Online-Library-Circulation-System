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
	    
		@NotBlank(message = "StudentID should not be Blank")
		private String StudentID;
		
		@NotBlank(message = "Firstname should not be Blank")
		private String firstname;
		
		@NotBlank(message = "Lastname should not be Blank")
		private String lastname;
		
		@NotBlank(message = "Department should not be Blank")
		private String department;
		
		@NotBlank(message = "Course should not be Blank")
		private String course;
		
		@NotBlank(message = "Email should not be Blank")
		@Email(message = "Email should be valid")
		private String email;
		
		@NotBlank(message = "BookTitle should not be Blank")
		private String booktitle;
	    
}
