package com.bank.sure.contoller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	public MessageController(MessageService messageService, ModelMapper modelMapper) {
//		this.messageService = messageService;
//		this.modelMapper = modelMapper;	
//	}
	
	private static Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private Message convertTo(MessageDTO messageDTO) {
		Message message = modelMapper.map(messageDTO, Message.class);
		return message;
	}
	
	private MessageDTO convertToDTO(Message message) {
		MessageDTO messageDTO=modelMapper.map(message, MessageDTO.class);
		return messageDTO;
	}
	
	@PostMapping
	public ResponseEntity<Response> createMessage(@Valid @RequestBody MessageDTO messageDTO){ //Valid annotation validates message according to 
		
		/*
		 * Message message =new Message(); message.setId(messageDTO.getId());
		 * message.setSubject(messageDTO.getSubject());
		 * message.setBody(messageDTO.getBody());
		 * message.setEmail(messageDTO.getEmail());
		 * message.setPhoneNumber(messageDTO.getPhoneNumber());
		 */
		Message message = convertTo(messageDTO);
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
	public ResponseEntity<List<MessageDTO>> getAll(){
		List<Message> allMessages = messageService.getAll();
		List<MessageDTO> messageList = allMessages.stream()
				.map(this::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(messageList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Long id){
		Message message = messageService.getMessage(id);
		MessageDTO messageDTO = convertToDTO(message);
		return ResponseEntity.ok(messageDTO);
	}
	
	@GetMapping("/request")
	public ResponseEntity<MessageDTO> getMessageByRequest(@RequestParam Long id){
		Message message = messageService.getMessage(id);
		MessageDTO messageDTO = convertToDTO(message);
		return ResponseEntity.ok(messageDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteMessage(@PathVariable("id") Long id){
		
		logger.info("Client wants to delete message id: {}", id);
		
		messageService.deleteMessage(id);
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Message deleted successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateMessage(@PathVariable("id") Long id, @Valid @RequestBody MessageDTO messageDTO){
		Message message = convertTo(messageDTO);
		messageService.updateMessage(id, message);
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Message updated successfully");
		return ResponseEntity.ok(response);	
	}
	
	
	
}
