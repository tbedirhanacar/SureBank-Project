package com.bank.sure.contoller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.sure.contoller.dto.MessageDTO;
import com.bank.sure.contoller.response.Response;
import com.bank.sure.domain.Message;
import com.bank.sure.service.MessageService;


@RestController

@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping
	public ResponseEntity<Response> createMessage(@Valid @RequestBody MessageDTO messageDTO){ //Valid annotation validates message according to 
		
		Message message =new Message();
		message.setId(messageDTO.getId());
		message.setSubject(messageDTO.getSubject());
		message.setBody(messageDTO.getBody());
		message.setEmail(messageDTO.getEmail());
		message.setPhoneNumber(messageDTO.getPhoneNumber());
		
		messageService.createMessage(message);
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Message saved successfully");
//		Map<String, String> map = new HashMap<>();
//		
//		map.put("message", "Message saved succesfully");
//		map.put("success", "true");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Message>> getAll(){
		List<Message> messages = messageService.getAll();
		return ResponseEntity.ok(messages);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessage(@PathVariable("id") Long id){
		Message message = messageService.getMessage(id);
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/request")
	public ResponseEntity<Message> getMessageByRequest(@RequestParam Long id){
		Message message = messageService.getMessage(id);
		return ResponseEntity.ok(message);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteMessage(@PathVariable("id") Long id){
		messageService.deleteMessage(id);
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Message deleted successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateMessage(@PathVariable("id") Long id, @Valid @RequestBody Message message){
		messageService.updateMessage(id, message);
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Message updated successfully");
		return ResponseEntity.ok(response);	
		
	}
	
	
}
