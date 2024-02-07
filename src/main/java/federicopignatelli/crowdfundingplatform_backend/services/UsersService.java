package federicopignatelli.crowdfundingplatform_backend.services;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class UsersService {
	@Autowired
	private UserRepository userRepository;

	public Page<User> getUsers(int page, int size, String orderBy) {
		if (size >= 100) size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
		return userRepository.findAll(pageable);
	}


	public User findById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public void findByIdAndDelete(UUID id) {
		User found = this.findById(id);
		userRepository.delete(found);
	}

	public User findByIdAndUpdate(UUID id, User body) {
		User found = this.findById(id);
		found.setSurname(body.getSurname());
		found.setName(body.getName());
		found.setEmail(body.getEmail());
		found.setPassword(body.getPassword());
		return userRepository.save(found);
	}


	public User findByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
	}


}
