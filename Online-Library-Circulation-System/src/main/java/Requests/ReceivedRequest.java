package Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedRequest {
	
    @NotBlank(message = "StudentID should be valid")
    private String studentID;
    
    private int bookId;      
    
    @Size(max = 20)
    private String status;

}
