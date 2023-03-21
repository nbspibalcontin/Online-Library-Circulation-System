package Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Approve_Entity")
public class Approveentity {

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

	private String status;

	private String booktitle;

	private LocalDateTime approvedAt;

	@PrePersist
	public void prePersist() {
		approvedAt = LocalDateTime.now();
	}
}
