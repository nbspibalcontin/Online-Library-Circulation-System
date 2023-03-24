package Requests.UpdateRequest;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnUpdateRequest {

	private long id;

	@NotBlank(message = "StudentID should be valid")
	@Size(max = 30)
	private String studentID;

	@NotBlank(message = "BookId should be valid")
	@Size(max = 30)
	private String bookId;

	@NotNull(message = "DueDate should be valid")
	private LocalDateTime dueDate;

	@NotNull(message = "ReturnDate should be valid")
	private LocalDateTime returnDate;


	@NotNull(message = "Fines should be valid")
	private double fines;
}
