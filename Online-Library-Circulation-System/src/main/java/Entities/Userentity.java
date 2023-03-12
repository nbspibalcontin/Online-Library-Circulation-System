package Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_Entity")
public class Userentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank
    @Size(max = 20)
    private String studentID;
    
    @NotBlank
    @Size(max = 20)
    private String firstname;
    
    @NotBlank
    @Size(max = 20)
    private String lastname;
    
    @NotBlank
    @Size(max = 20)
    private String department;
    
    @NotBlank
    @Size(max = 20)
    private String course;
    
    @NotBlank
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank
    @Size(max = 120)
    private String password;
    
    @NotBlank
    @Size(max = 20)
    private String roles;
}
