package Entities;

import java.time.LocalDateTime;

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
@Table(name = "ReceivedBook_Entity")
public class ReceivedBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String studentID;

	private String bookId;

	private String status;

	private LocalDateTime dueDate;

}
