package federicopignatelli.crowdfundingplatform_backend.services;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.entities.enums.Role;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.exceptions.UnauthorizedException;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.UserLoginDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import federicopignatelli.crowdfundingplatform_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcrypt;

	@Autowired
	private JWTTools jwtTools;

	public String authenticateUser(UserLoginDTO body) {

		User user = usersService.findByEmail(body.email());
		if (bcrypt.matches(body.password(), user.getPassword())) {
			return jwtTools.createToken(user);
		} else {
			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

	public User save(NewUserDTO body) {
		userRepository.findByEmail(body.email()).ifPresent(user -> {
			throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
		});

		User newUser = new User();
		newUser.setSurname(body.surname());
		newUser.setName(body.name());
		newUser.setEmail(body.email());
		newUser.setPassword(bcrypt.encode(body.password()));
		newUser.setRole(Role.USER);
		newUser.setCountry(body.country());
		newUser.setCity(body.city());
		newUser.setBio(body.bio());
		newUser.setProfilepic(body.profilepic());
		return userRepository.save(newUser);
	}
}
