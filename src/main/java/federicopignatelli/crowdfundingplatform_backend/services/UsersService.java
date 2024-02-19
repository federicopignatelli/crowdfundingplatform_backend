package federicopignatelli.crowdfundingplatform_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import federicopignatelli.crowdfundingplatform_backend.cloudinaryconfig.Cloudinaryconfig;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.user.NewUserUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

@Service
public class UsersService {

	@Autowired
	private Cloudinary cloudinary;
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

	public NewUserUpdateResponseDTO findByIdAndUpdate(UUID id, NewUserUpdateDTO body) {
		User found = this.findById(id);

		if(body.name() != null){
			found.setName(body.name());
		}

		if(body.surname() != null){
			found.setSurname(body.surname());
		}

		if(body.email() != null){
			found.setEmail(body.email());
		}

		if(body.country() != null){
			found.setCountry(body.country());
		}

		if(body.city() != null){
			found.setCity(body.city());
		}

		if(body.bio() != null){
			found.setBio(body.bio());
		}

		userRepository.save(found);
		return new NewUserUpdateResponseDTO();
	}

	public String uploadAvatar(MultipartFile file, UUID id) throws IOException {
		User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		String url = (String) cloudinary.uploader()
				.upload(file.getBytes(), ObjectUtils.emptyMap())
				.get("url");
		found.setProfilepic(url);
		userRepository.save(found);
		return url;
	}


	public User findByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
	}


}
