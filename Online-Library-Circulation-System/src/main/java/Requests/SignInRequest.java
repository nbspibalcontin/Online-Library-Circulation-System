package Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
	
	@NotBlank(message = "Email should be valid")
    private String email ;
	@NotBlank(message = "Password should be valid")
    private String password;
}
