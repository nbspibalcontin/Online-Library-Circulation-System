package Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
	@NotBlank
    private String email ;
	@NotBlank
    private String password;
}
