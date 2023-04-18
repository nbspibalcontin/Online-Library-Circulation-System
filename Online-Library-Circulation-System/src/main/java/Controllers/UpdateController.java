package Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Entities.Approveentity;
import Entities.BookLostentity;
import Entities.Bookentity;
import Entities.ReceivedBook;
import Entities.Reserveentity;
import Entities.Returnentity;
import Entities.Successfulentity;
import Entities.Userentity;
import ExeptionHandler.NotFoundExceptionHandler.NotFoundException;
import Reponses.ErrorResponse;
import Reponses.MessageResponse;
import Repositories.ApproveentityRepository;
import Repositories.BookEntityRepository;
import Repositories.BookLostentityRepository;
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;
import Requests.UpdateRequest.ApproveUpdateRequest;
import Requests.UpdateRequest.BookLostUpdateRequest;
import Requests.UpdateRequest.BookUpdateRequest;
import Requests.UpdateRequest.ReceivedUpdateRequest;
import Requests.UpdateRequest.ReserveUpdateRequest;
import Requests.UpdateRequest.ReturnUpdateRequest;
import Requests.UpdateRequest.SuccessfulTransactionUpdateRequest;
import Requests.UpdateRequest.UserUpdateRequest;
import Services.UpdateServices.UpdateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RequestMapping("/api")
public class UpdateController {

	@Autowired
	private BookEntityRepository bookEntityRepository;

	@Autowired
	private ReserveEntityRepository reserveEntityRepository;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private ApproveentityRepository approveentityRepository;

	@Autowired
	private ReceivedBookRepository receivedBookRepository;

	@Autowired
	private ReturnEntityRepository returnEntityRepository;

	@Autowired
	private SuccessfulEntityRepository successfulEntityRepository;

	@Autowired
	private BookLostentityRepository bookLostentityRepository;

	@Autowired
	private UpdateService updateService;

	// UPDATE BOOK //

	@PutMapping("/updatebook/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateTheBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest updateRequest,
			BindingResult bindingResult) {
		try {
			Bookentity book = bookEntityRepository.findByid(id);

			if (book == null) {
				throw new NotFoundException("Book not found with ID: " + id);
			}

			// Check for validation errors
			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.updateBook(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// UPDATE RESERVATION //

	@PutMapping("/updatereserve/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> UpdateTheReservation(@PathVariable Long id,
			@Valid @RequestBody ReserveUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			// Check if reservation exists
			Reserveentity reserve = reserveEntityRepository.findByid(id);

			if (reserve == null) {
				throw new NotFoundException("Reservation request not found with ID: " + id);
			}

			// Check for validation errors
			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateReserve(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE BOOK LOST //

	@PutMapping("/updatebooklost/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheBooklost(@PathVariable Long id,
			@Valid @RequestBody BookLostUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			// Check if reservation exists
			BookLostentity bookLost = bookLostentityRepository.findByid(id);

			if (bookLost == null) {
				throw new NotFoundException("Book lost not found with ID: " + id);
			}

			// Check for validation errors
			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.updateBooklost(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE APPROVE REQUEST //

	@PutMapping("/updateapprove/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheAprrove(@PathVariable Long id,
			@Valid @RequestBody ApproveUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			Approveentity approve = approveentityRepository.findByid(id);

			if (approve == null) {
				throw new NotFoundException("Approve Request not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateApprove(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}
	}

	// UPDATE RECEIVED BOOK //

	@PutMapping("/updatereceived/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheReceivedbook(@PathVariable Long id,
			@Valid @RequestBody ReceivedUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			ReceivedBook received = receivedBookRepository.findByid(id);

			if (received == null) {
				throw new NotFoundException("Received book not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateReceivedBook(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE RETURNED BOOK //

	@PutMapping("/updatereturned/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheReturnedbook(@PathVariable Long id,
			@Valid @RequestBody ReturnUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			Returnentity returned = returnEntityRepository.findByid(id);
			if (returned == null) {
				throw new NotFoundException("Return book not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateReturnedBook(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE SUCCESSFUL TRANSACTION BOOK //

	@PutMapping("/updatesuccess/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheSuccessfulTransaction(@PathVariable Long id,
			@Valid @RequestBody SuccessfulTransactionUpdateRequest updateRequest, BindingResult bindingResult) {
		try {
			Successfulentity successful = successfulEntityRepository.findByid(id);

			if (successful == null) {
				throw new NotFoundException("Successful transaction not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateSuccessfualTransaction(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE USER //

	@PutMapping("/updateuser/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> UpdateTheUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest updateRequest,
			BindingResult bindingResult) {
		try {
			Userentity user = userEntityRepository.findByid(id);

			if (user == null) {
				throw new NotFoundException("User not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateUser(id, updateRequest);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

	// UPDATE USER //

	@PutMapping("/updateimage")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> UpdateTheImage(@RequestParam("image") MultipartFile file, Long id) {
		try {
			MessageResponse messageResponse = updateService.Updateimage(file, id);

			return ResponseEntity.ok().body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("An error occurred: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new MessageResponse("An unexpected error occurred: " + e.getMessage()));
		}

	}

}
