package com.meta.store.werehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Client;

@RestController
@RequestMapping("/werehouse/client")
public class ClientController {

	@Autowired
	private BaseService<Client, Long> baseService;
	
	@GetMapping("/get")
	public List<Client> getAll(){
		return baseService.getAll();
	}
}
