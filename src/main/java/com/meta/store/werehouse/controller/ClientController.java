package com.meta.store.werehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Client;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/werehouse/client")
@RequiredArgsConstructor
public class ClientController {

	
	private final BaseService<Client, Long> baseService;
	
	@GetMapping("/get")
	public List<Client> getAll(){
		return baseService.getAll();
	}
	
	@PostMapping("/add")
	public ResponseEntity<Client> insertClient(@RequestBody Client client){
		baseService.insert(client);
		return new ResponseEntity<Client>(HttpStatus.ACCEPTED);
				}
}
