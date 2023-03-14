package Entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ReceivedBook_Entity")
public class ReceivedBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @Size(max = 20)
    private String studentID;
    
    private int bookId;
       
    private LocalDate dueDate;
    
    @Size(max = 20)
    private String status;
    
    @PrePersist
    public void prePersist() {
        this.dueDate = LocalDate.now().plusDays(3);
    }
    
    
}
