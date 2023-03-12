package Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRequest {

		private Long bookId;
	    
	    @NotBlank
		private String StudentID;
		
	    @NotBlank
		private String firstname;
		
	    @NotBlank
		private String lastname;
		
	    @NotBlank
		private String department;
		
	    @NotBlank
		private String course;
		
	    @NotBlank
		private String email;
		
	    @NotBlank
		private String booktitle;
	    
}
