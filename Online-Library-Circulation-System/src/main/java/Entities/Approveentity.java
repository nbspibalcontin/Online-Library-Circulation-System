package Entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
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
	
    @Size(max = 30)
	private String studentID;
	
    @Size(max = 30)
	private String firstname;
	
    @Size(max = 30)
	private String lastname;
	
    @Size(max = 30)
	private String department;
	
    @Size(max = 30)
	private String course;
	
	@Size(max = 50)
	private String email;
	
    @Size(max = 30)
	private String status;
    
    @Size(max = 30)
	private String booktitle;
    
    @Column(name = "Approved_At")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime approvedAt;
    
}
