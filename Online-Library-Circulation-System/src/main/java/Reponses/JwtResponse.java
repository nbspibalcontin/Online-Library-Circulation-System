package Reponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private String token;
	private long id;
    private String studentID;
    private String firstname;
    private String lastname;
    private String department;
    private String course;
    private String email;
    private String roles;

}
