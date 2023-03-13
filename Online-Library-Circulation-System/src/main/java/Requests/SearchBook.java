package Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBook {
	
	@NotBlank(message = "Filter should be valid")
	private String filter;
	
	@NotBlank(message = "Keyword should be Blank")
	private String keyword;

}
