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
    
    @NotBlank(message = "StudentID should be valid")
    @Size(max = 20)
    private String studentID;
    
    @NotBlank(message = "Firstname should be valid")
    @Size(max = 20)
    private String firstname;
    
    @NotBlank(message = "Lastname should be valid")
    @Size(max = 20)
    private String lastname;
    
    @NotBlank(message = "Department should be valid")
    @Size(max = 20)
    private String department;
    
    @NotBlank(message = "Course should be valid")
    @Size(max = 20)
    private String course;
    
    @NotBlank
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password should be valid")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    @NotBlank(message = "Role should be valid")
    @Size(max = 20)
    private String roles;
    
    private int booksBorrowed;
}
