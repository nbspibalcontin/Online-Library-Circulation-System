package Requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    private String email ;
    private String password;
}
