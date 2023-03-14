package Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Approve_Entity")
public class Approveentity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long bookId;
	
	private String studentID;
	
	private String firstname;
	
	private String lastname;
	
	private String department;
	
	private String course;
	
	private String email;
	
	private String status;
    
	private String booktitle;
    
    private String approvedAt;

    
}
