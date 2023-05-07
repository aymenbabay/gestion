package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.NotPermissonException;
import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.dto.ClientDto2;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.ClientMapper;
import com.meta.store.werehouse.mapper.ClientMapper2;
import com.meta.store.werehouse.repository.ClientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService extends BaseService<Client, Long> {


	private final ClientMapper clientMapper;
	
	private final ClientMapper2 clientMapper2;
	
	private final ClientRepository clientRepository;

	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;

	
	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<ClientDto> insertClient(@Valid ClientDto clientDto) {
		Company company = getCompany();
		if(company == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One first");
		}else {
		Optional<Client> client2 = clientRepository.findByCode(clientDto.getCode());
		if( client2.isEmpty())  {
				Set<Company> companies = new HashSet<>();
				companies.add(company);
				Client client = clientMapper.mapToEntity(clientDto);
				client.setCompanies(companies);
				super.insert(client);
				return new ResponseEntity<ClientDto>(HttpStatus.ACCEPTED);
		}else 
			throw new RecordIsAlreadyExist("Client Code Is Already Exist Please Choose Another One");
		}
	}
	

	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<String> addExistClient(Long id, Company company) {
		ResponseEntity<Client> client = super.getById(id);
		if(client != null) {
			for(Company i :client.getBody().getCompanies()) {
				if(i == company) {
					throw new RecordIsAlreadyExist("This Client Is Already Exist");
				}
			}
			Set<Company> companies = new HashSet<>();
			companies.add(company);
			companies.addAll(client.getBody().getCompanies());
			client.getBody().setCompanies(companies);
			super.insert(client.getBody());
			return null;
		}else 
		throw new RecordNotFoundException("This Client Is Not Exist Please Create it For You");
		
	}



	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<ClientDto2> addMeAsClientExist(ClientDto2 clientDto, Company company) {
		Optional<Client> client2 = clientRepository.findByUserId(company.getUser().getId());
		if(client2.isPresent()) {
			throw new RecordIsAlreadyExist("You Are Already Client");
		}
		Optional<Client> client = clientRepository.findByCode(clientDto.getCode());
		if(client.isPresent()) {
			throw new RecordIsAlreadyExist("This Client Code Is Already Exist");
		}
		Client client1 = clientMapper2.mapToEntity(clientDto);
		client1.setUser(company.getUser());
		super.insert(client1);
		return null;
	}

	@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	public List<ClientDto> getMybyCompanyId(Company company) {
		List<Client> client = clientRepository.getAllByCompanyId(company.getId());
		if(client.isEmpty()) {
			throw new RecordNotFoundException("You Have No Client");
		}
		List<ClientDto> clientDto = new ArrayList<>();
		for(Client i : client) {
		ClientDto clientDto1 = clientMapper.mapToDto(i);
		clientDto.add(clientDto1);
		}
		return clientDto;
	}

	@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	public ClientDto getMyByCodeAndCompanyId(@Valid String code, Company company) {
		Optional<Client> client = clientRepository.findByCodeAndCompanyId(code,company.getId());
		if(client.isPresent()) {
			ClientDto clientDto = clientMapper.mapToDto(client.get());
			return clientDto;
		}else throw new RecordNotFoundException("There Is No Client Has Code : "+code);
	}



	@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	public List<ClientDto> getMyByNameAndCompanyId(@Valid String name, Company company) {
		List<Client> client = clientRepository.findByNameAndCompanyId(name,company.getId());
		if(client.isEmpty()) {
			throw new RecordNotFoundException("There Is No Client With Name : "+name);	
		}
		List<ClientDto> clientsDto = new ArrayList<>();
		for(Client i : client) {
		ClientDto clientDto = clientMapper.mapToDto(i);
		clientsDto.add(clientDto);
		}
		return clientsDto;
	}


	//@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	// for developpers
	public List<ClientDto> getAllClient() {
		List<Client> clients = super.getAll();
		if(clients == null) {
			throw new RecordNotFoundException("There Is No Client Yet");
		}
		List<ClientDto> clientsDto = new ArrayList<>();
		for(Client i : clients) {
			ClientDto clientDto = clientMapper.mapToDto(i);
			clientsDto.add(clientDto);
		}
		return clientsDto;
	}


	@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	public ClientDto getClientByCode(String code) {
		Optional<Client> client = clientRepository.findByCode(code);
		if(client.isEmpty()) {
			throw new RecordNotFoundException("There Is No Client With Code: "+code);
		}
		
			ClientDto clientDto = clientMapper.mapToDto(client.get());
			return clientDto;
			
		
	}


	@Cacheable(value = "client", key = "#root.methodName + '_' + #company.id")
	public List<ClientDto> getAllClientByName(String name) {
		List<Client> clients = clientRepository.findByName(name);
		if(clients.isEmpty()) {
			throw new RecordNotFoundException("There Is No Client With Name: "+name);
		}
			List<ClientDto> clientsDto = new ArrayList<>();
			for(Client i : clients) {
			ClientDto clientDto = clientMapper.mapToDto(i);
			clientsDto.add(clientDto);
			}
			return clientsDto;
	}


	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ClientDto upDateMyClientById(Long id, ClientDto clientDto, Company company, Long userId,String username) {
		ResponseEntity<Client> client = super.getById(id);
		if(client == null) {
			throw new RecordNotFoundException("Client Not Found");
		}
		
		if( client.getBody().getUser() != null) {
			if(client.getBody().getUser().getId() != userId) {
				throw new NotPermissonException("You Have No Permission");
			}
		
		}
		if(!client.getBody().getCreatedBy().equals(username)) {
			System.out.println(username);
			throw new NotPermissonException("You Have No Permission");
		}
			Optional<Client> client1 = clientRepository.findByCode(clientDto.getCode());
			if(client1.isPresent() && client1.get().getId() != id) {
				throw new RecordIsAlreadyExist("This Client Code Is Already Exist Please Choose Another One");
			}
			Client client3 = clientMapper.mapToEntity(clientDto);
			Set<Company> companies = new HashSet<>();
			companies.add(company);
			client3.setCompanies(companies);
			super.insert(client3);
			return clientDto;
		
	}


	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public void deleteClientById(Long id, Long userId, Company company) {
		ResponseEntity<Client> client = super.getById(id);
	        if (client == null || company == null) {
	        	throw new RecordNotFoundException("Client Not Found ");
	        }

            client.getBody().getCompanies().remove(company);
            client.getBody().setUser(null);;
            clientRepository.save(client.getBody());
		
		
	}


	public Optional<Client> getByUserId(Long userId) {
		Optional<Client> client = clientRepository.findByUserId(userId);
		return client;
	}


	@CacheEvict(value = "client", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public void addMeAsClient(Company company, String code) {
		Optional<Client> client = clientRepository.findByUserId(company.getUser().getId());
		if(client.isPresent()) {
			throw new RecordIsAlreadyExist("You Are Already Client");
		}
		Optional<Client> client1 = clientRepository.findByCode(code);
		if(client1.isPresent()) {
			throw new RecordIsAlreadyExist("This Code is already found Please Try another Code");
		}
		Client meClient = new Client();
		meClient.setCode(code);
		meClient.setName(company.getName());
		meClient.setAddress(company.getAddress());
		meClient.setCredit((double)0);
		meClient.setEmail(company.getEmail());
		meClient.setMvt((double)0);
		meClient.setNature("personne Moral");
		meClient.setPhone(company.getPhone());
		Set<Company> companies = new HashSet<>();
		companies.add(company);
		meClient.setCompanies(companies);
		meClient.setUser(company.getUser());
		clientRepository.save(meClient);
		
	}
	
	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			return company;
		}
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
			
	}
	

}
