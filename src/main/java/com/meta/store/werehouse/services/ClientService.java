package com.meta.store.werehouse.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Client;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientService extends BaseService<Client, Long> {

	@Override
	public ResponseEntity<Client> insert(Client entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}
}
