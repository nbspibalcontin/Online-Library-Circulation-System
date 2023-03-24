package Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Entities.Approveentity;
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
import Repositories.ReceivedBookRepository;
import Repositories.ReserveEntityRepository;
import Repositories.ReturnEntityRepository;
import Repositories.SuccessfulEntityRepository;
import Repositories.UserEntityRepository;
import Requests.UpdateRequest.ApproveUpdateRequest;
import Requests.UpdateRequest.BookUpdateRequest;
import Requests.UpdateRequest.ReceivedUpdateRequest;
import Requests.UpdateRequest.ReserveUpdateRequest;
import Requests.UpdateRequest.ReturnUpdateRequest;
import Requests.UpdateRequest.SuccessfulTransactionUpdateRequest;
import Requests.UpdateRequest.UserUpdateRequest;
import Services.UpdateServices.UpdateService;
import jakarta.validation.Valid;

@RestController
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
	private UpdateService updateService;

	// UPDATE BOOK //

	@PutMapping("/updatebook/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateTheBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest updateRequest,
			BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}
	}

	// UPDATE RESERVATION //

	@PutMapping("/updatereserve/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> UpdateTheReservation(@PathVariable Long id,
			@Valid @RequestBody ReserveUpdateRequest updateRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}

	}

	// UPDATE APPROVE REQUEST //

	@PutMapping("/updateapprove/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheAprrove(@PathVariable Long id,
			@Valid @RequestBody ApproveUpdateRequest updateRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}
	}

	// UPDATE RECEIVED BOOK //

	@PutMapping("/updatereceived/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheReceivedbook(@PathVariable Long id,
			@Valid @RequestBody ReceivedUpdateRequest updateRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}

	}

	// UPDATE RETURNED BOOK //

	@PutMapping("/updatereturned/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheReturnedbook(@PathVariable Long id,
			@Valid @RequestBody ReturnUpdateRequest updateRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

		Returnentity returned = returnEntityRepository.findByid(id);

		try {
			if (returned == null) {
				throw new NotFoundException("Return book not found with ID: " + id);
			}

			if (bindingResult.hasErrors()) {
				List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.toList());
				return ResponseEntity.badRequest().body(new ErrorResponse(errors));
			}

			MessageResponse messageResponse = updateService.UpdateReturnedBook(id, updateRequest);

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}

	}

	// UPDATE SUCCESSFUL TRANSACTION BOOK //

	@PutMapping("/updatesuccess/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> UpdateTheSuccessfulTransaction(@PathVariable Long id,
			@Valid @RequestBody SuccessfulTransactionUpdateRequest updateRequest, BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}

	}

	// UPDATE USER //

	@PutMapping("/updateuser/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> UpdateTheUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest updateRequest,
			BindingResult bindingResult) {

		// Custom HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer token");

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

			return ResponseEntity.ok().headers(headers).body(messageResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
		}

	}

}
