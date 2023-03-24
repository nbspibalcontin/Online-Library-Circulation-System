package Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRequest {

	@NotBlank(message = "Filter should be valid")
	private String bookId;

	@NotBlank(message = "Filter should be valid")
	private String studentID;

}
