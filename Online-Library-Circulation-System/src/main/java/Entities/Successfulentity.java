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
@Table(name = "Successful_Entity")
public class Successfulentity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String bookId;

	private String studentID;

	private String firstname;

	private String lastname;

	private String department;

	private String course;

	private String email;

	private LocalDateTime dueDate;

	private LocalDateTime returnDate;

	private double fines;

}
