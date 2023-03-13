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
@Table(name = "Book_Entity")
public class Bookentity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookId;
	
	@NotBlank(message = "Title should be valid")
    @Size(max = 30)
	private String title;
    
	@NotBlank(message = "Author should be valid")
    @Size(max = 30)
	private String author;
    
	@NotBlank(message = "Subject should be valid")
    @Size(max = 1000)
	private String subject;
    
	@NotBlank(message = "DatePublish should be valid")
    @Size(max = 20)
	private String datepublish;
    
    private int quantity;
    
    
}
