package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersService usersService;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
							   @RequestParam(defaultValue = "10") int size,
							   @RequestParam(defaultValue = "id") String orderBy) {
		return usersService.getUsers(page, size, orderBy);
	}


	@GetMapping("/me")
	@PreAuthorize("hasAuthority('USER')")
	public User getProfile(@AuthenticationPrincipal User currentUser) {
		return currentUser;
	}

	@PutMapping("/me")
	@PreAuthorize("hasAuthority('USER')")
	public User getMeAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody User body) {
		return usersService.findByIdAndUpdate(currentUser.getUserId(), body);
	}

	@DeleteMapping("/me")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void getMeAnDelete(@AuthenticationPrincipal User currentUser) {
		usersService.findByIdAndDelete(currentUser.getUserId());
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable UUID userId) {
		return usersService.findById(userId);
	}


	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('USER')")
	public User getUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody User modifiedUserPayload) {
		return usersService.findByIdAndUpdate(userId, modifiedUserPayload);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void getUserByIdAndDelete(@PathVariable UUID userId) {
		usersService.findByIdAndDelete(userId);
	}


}
