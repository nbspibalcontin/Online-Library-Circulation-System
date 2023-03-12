package Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reserve_Entity")
public class Reserveentity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @NotBlank
    @Size(max = 30)
	private String StudentID;
	
    @NotBlank
    @Size(max = 30)
	private String firstname;
	
    @NotBlank
    @Size(max = 30)
	private String lastname;
	
    @NotBlank
    @Size(max = 30)
	private String department;
	
    @NotBlank
    @Size(max = 30)
	private String course;
	
    @NotBlank
    @Size(max = 30)
	private String email;
	
    @Size(max = 30)
	private String status;
    
    @NotBlank
    @Size(max = 30)
	private String booktitle;
}
