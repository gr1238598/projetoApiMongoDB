package com.gabriel.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.workshopmongo.domain.User;
import com.gabriel.workshopmongo.dto.UserDTO;
import com.gabriel.workshopmongo.repository.UserRepository;
import com.gabriel.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User>findAll(){
		return userRepository.findAll();
		
	}
	public User insert(User user) {
		return userRepository.save(user);
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(),objDto.getEmail());
		
	}
	
	public User findById(String id) {
		Optional<User>obj = userRepository.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado"));
		
	}
	
	public void delete(String id) {
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User user) {
		Optional<User> obj = userRepository.findById(user.getId());
		
		obj.get().setName(user.getName());
		obj.get().setEmail(user.getEmail());
		userRepository.save(obj.get());
		 
		 return obj.get();
	}
	
}
