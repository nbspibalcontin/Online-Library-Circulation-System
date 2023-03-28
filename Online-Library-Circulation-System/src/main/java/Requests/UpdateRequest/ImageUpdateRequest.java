package Requests.UpdateRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUpdateRequest {

	@NotNull
	private byte[] imageData;

	private long id;
}
