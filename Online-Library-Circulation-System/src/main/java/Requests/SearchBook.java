package Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBook {
	
	@NotBlank
	private String filter;
	
	@NotBlank
	private String keyword;

}
