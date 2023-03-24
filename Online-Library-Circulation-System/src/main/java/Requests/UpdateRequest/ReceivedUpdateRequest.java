package Requests.UpdateRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedUpdateRequest {

	private long id;

	@NotBlank(message = "StudentID should be valid")
	@Size(max = 30)
	private String studentID;

	@NotBlank(message = "BookId should be valid")
	@Size(max = 30)
	private String bookId;

	@NotBlank(message = "Status should be valid")
	@Size(max = 30)
	private String status;

}
