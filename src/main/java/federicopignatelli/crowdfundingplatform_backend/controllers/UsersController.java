package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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



	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('USER')")
    public NewUserUpdateResponseDTO findByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated NewUserUpdateDTO body, BindingResult validation) throws BadRequestException {
	if (validation.hasErrors()) {
		throw new BadRequestException(validation.getAllErrors().toString());
	}
	    usersService.findByIdAndUpdate(userId, body);
	    return new NewUserUpdateResponseDTO();
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

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void getUserByIdAndDelete(@PathVariable UUID userId) {
		usersService.findByIdAndDelete(userId);
	}


}
