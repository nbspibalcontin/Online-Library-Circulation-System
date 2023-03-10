package Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String studentID;
    private String firstname;
    private String lastname;
    private String department;
    private String course;
    private String email;
    private String password;
    private String roles;
}
