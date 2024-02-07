package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.UserLoginDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.UserLoginResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
		String accessToken = authService.authenticateUser(body);
		return new UserLoginResponseDTO(accessToken);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) {
		System.out.println(validation);
		if (validation.hasErrors()) {
			System.out.println(validation.getAllErrors());
			throw new BadRequestException("Ci sono errori nel payload!");
		} else {
			User newUser = authService.save(newUserPayload);

			return new NewUserResponseDTO(newUser.getId());
		}
	}
}
