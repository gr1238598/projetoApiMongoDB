package com.gabriel.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gabriel.workshopmongo.domain.Post;
import com.gabriel.workshopmongo.domain.User;
import com.gabriel.workshopmongo.dto.UserDTO;
import com.gabriel.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	//pode usar @GetMapping()
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User>list = service.findAll();
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		
	}
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User user = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(user));
		
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<User>insert(@RequestBody UserDTO userDto){
		User obj = service.fromDTO(userDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Void>delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method=RequestMethod.PUT, value= "/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO userDto, @PathVariable String id){
		User user = service.fromDTO(userDto);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.noContent().build();
		
	}
	@RequestMapping(method = RequestMethod.GET, value="/{id}/posts")
	public ResponseEntity<List<Post>>findPosts(@PathVariable String id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getPosts());
		
	}
}
