package Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookentity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @NotBlank
    @Size(max = 30)
	private String title;
    
    @NotBlank
    @Size(max = 30)
	private String author;
    
    @NotBlank
    @Size(max = 1000)
	private String subject;
    
    @NotBlank
    @Size(max = 20)
	private String datepublish;
    
    @Min(value = 1, message = "Value must be greater than or equal to 1")
    private long quantity;
    
    
}
