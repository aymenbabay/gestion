package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.FournisseurDto;
import com.meta.store.werehouse.dto.FournisseurDto2;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.FournisseurMapper;
import com.meta.store.werehouse.mapper.FournisseurMapper2;
import com.meta.store.werehouse.repository.FournisseurRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FournisseurService extends BaseService<Fournisseur, Long> {

	private final FournisseurMapper fournisseurMapper;
	
	private final FournisseurRepository fournisseurRepository;
	
	private final FournisseurMapper2 fournisseurMapper2;

	@CacheEvict(value = "fournisseur", key = "#root.methodName", allEntries = true)
	public ResponseEntity<FournisseurDto> insertFournisseur( FournisseurDto fournisseurDto, Company company) {
		if(company == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One first");
		}else {
		Optional<Fournisseur> fournisseur2 = fournisseurRepository.findByCode(fournisseurDto.getCode());
		if( fournisseur2.isEmpty())  {
				Set<Company> companies = new HashSet<>();
				companies.add(company);
				Fournisseur fournisseur = fournisseurMapper.mapToEntity(fournisseurDto);
				fournisseur.setCompanies(companies);
				super.insert(fournisseur);
				return new ResponseEntity<FournisseurDto>(HttpStatus.ACCEPTED);
		}else 
			throw new RecordIsAlreadyExist("Fournisseur Code Is Already Exist Please Choose Another One");
		}
	}
	

	@CacheEvict(value = "fournisseur", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<String> addExistFournisseur(Long id, Company company) {
		ResponseEntity<Fournisseur> fournisseur = super.getById(id);
		if(fournisseur != null) {
			for(Company i :fournisseur.getBody().getCompanies()) {
				if(i == company) {
					throw new RecordIsAlreadyExist("This Fournisseur Is Already Exist");
				}
			}
			Set<Company> companies = new HashSet<>();
			companies.add(company);
			companies.addAll(fournisseur.getBody().getCompanies());
			fournisseur.getBody().setCompanies(companies);
			super.insert(fournisseur.getBody());
			return null;
		}else 
		throw new RecordNotFoundException("This Fournisseur Is Not Exist Please Create it For You");
		
	}



	@CacheEvict(value = "fournisseur", key = "#root.methodName + '_' + #company.id")
	public ResponseEntity<FournisseurDto2> addMeAsFournisseur(Company company) {
		Optional<Fournisseur> fournisseur2 = fournisseurRepository.findByUserId(company.getUser().getId());
		if(fournisseur2.isPresent()) {
			throw new RecordIsAlreadyExist("You Are Already Fournisseur");
		}
		Optional<Fournisseur> fournisseur = fournisseurRepository.findByCode(company.getCode());
		if(fournisseur.isPresent()) {
			throw new RecordIsAlreadyExist("This Fournisseur Code Is Already Exist Please Choose Another One");
		}
		Fournisseur fournisseur1 = fournisseurMapper.mapCompanyToFournisseur(company);
		Set<Company> companies = new HashSet<>();
		fournisseur1.setUser(company.getUser());
		fournisseur1.setCredit((double)0);
		fournisseur1.setMvt((double)0);
		fournisseur1.setNature("personne Moral");
		companies.add(company);
		fournisseur1.setCompanies(companies);
		super.insert(fournisseur1);
		return null;
	}

	@Cacheable(value = "fournisseur", key = "#root.methodName + '_' + #company.id")
	public List<FournisseurDto2> getMybyCompanyId(Company company) {
		List<Fournisseur> fournisseur = fournisseurRepository.getAllByCompanyId(company.getId());
		if(fournisseur.isEmpty()) {
			throw new RecordNotFoundException("You Have No Fournisseur");
		}
		List<FournisseurDto2> fournisseurDto2 = new ArrayList<>();
		for(Fournisseur i : fournisseur) {
		FournisseurDto2 fournisseurDto = fournisseurMapper2.mapToDto(i);
		fournisseurDto2.add(fournisseurDto);
		}
		return fournisseurDto2;
	}

	@Cacheable(value = "fournisseur", key = "#root.methodName + '_' + #company.id")
	public FournisseurDto getMyByCodeAndCompanyId(@Valid String code, Company company) {
		Optional<Fournisseur> fournisseur = fournisseurRepository.findByCodeAndCompanyId(code,company.getId());
		if(fournisseur.isPresent()) {
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(fournisseur.get());
			return fournisseurDto;
		}else throw new RecordNotFoundException("There Is No Fournisseur Has Code : "+code);
	}

	@Cacheable(value = "fournisseur", key = "#root.methodName + '_' + #company.id")
	public List<FournisseurDto> getMyByNameAndCompanyId(@Valid String name, Company company) {
		List<Fournisseur> fournisseur = fournisseurRepository.findByNameAndCompanyId(name,company.getId());
		if(fournisseur.isEmpty()) {
			throw new RecordNotFoundException("There Is No Fournisseur With Name : "+name);	
		}
		List<FournisseurDto> fournisseursDto = new ArrayList<>();
		for(Fournisseur i : fournisseur) {
		FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(i);
		fournisseursDto.add(fournisseurDto);
		}
		return fournisseursDto;
	}


	@Cacheable(value = "fournisseur", key = "#root.methodName")
	public List<FournisseurDto> getAllFournisseur() {
		List<Fournisseur> fournisseurs = super.getAll();
		if(fournisseurs == null) {
			throw new RecordNotFoundException("There Is No Fournisseur Yet");
		}
		List<FournisseurDto> fournisseursDto = new ArrayList<>();
		for(Fournisseur i : fournisseurs) {
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(i);
			fournisseursDto.add(fournisseurDto);
		}
		return fournisseursDto;
	}


	//@Cacheable(value = "fournisseur", key = "#root.methodName")
	public FournisseurDto getFournisseurByCode(String code) {
		Optional<Fournisseur> fournisseur = fournisseurRepository.findByCode(code);
		if(fournisseur.isEmpty()) {
			throw new RecordNotFoundException("There Is No Fournisseur With Code: "+code);
		}
		
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(fournisseur.get());
			return fournisseurDto;
			
		
	}


	//@Cacheable(value = "fournisseur", key = "#root.methodName")
	public List<FournisseurDto> getAllFournisseurByName(String name) {
		List<Fournisseur> fournisseurs = fournisseurRepository.findByName(name);
		if(fournisseurs.isEmpty()) {
			throw new RecordNotFoundException("There Is No Fournisseur With Name: "+name);
		}
			List<FournisseurDto> fournisseursDto = new ArrayList<>();
			for(Fournisseur i : fournisseurs) {
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(i);
			fournisseursDto.add(fournisseurDto);
			}
			return fournisseursDto;
	}


	@CacheEvict(value = "fournisseur", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public FournisseurDto upDateMyFournisseurById(Long id, FournisseurDto fournisseurDto, Company company) {
		ResponseEntity<Fournisseur> fournisseur = super.getById(id);
		if(fournisseur == null) {
			throw new RecordNotFoundException("Fournisseur Not Found");
		}
		
		if( fournisseur.getBody().getUser() != null) {
			if(fournisseur.getBody().getUser().getId() != company.getUser().getId()) {
				throw new NotPermissonException("You Have No Permission");
			}
		
		}
		if(!fournisseur.getBody().getCreatedBy().equals(company.getUser().getUsername())) {
			throw new NotPermissonException("You Have No Permission");
		}
			Optional<Fournisseur> fournisseur1 = fournisseurRepository.findByCode(fournisseurDto.getCode());
			if(fournisseur1.isPresent() && fournisseur1.get().getId() != id) {
				throw new RecordIsAlreadyExist("This Fournisseur Code Is Already Exist Please Choose Another One");
			}
			Fournisseur fournisseur3 = fournisseurMapper.mapToEntity(fournisseurDto);
			Set<Company> companies = new HashSet<>();
			companies.add(company);
			fournisseur3.setCompanies(companies);
			super.insert(fournisseur3);
			return fournisseurDto;
		
	}


	@CacheEvict(value = "fournisseur", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public void deleteFournisseurById(Long id, Long userId, String userName, Company company) {
		ResponseEntity<Fournisseur> fournisseur = super.getById(id);
	        if (fournisseur == null || company == null) {
	        	throw new RecordNotFoundException("Fournisseur Not Found ");
	        }

            fournisseur.getBody().getCompanies().remove(company);
            fournisseurRepository.save(fournisseur.getBody());
		
		
	}


	public void addMeAsProvider(Company company, String code) {
		Optional<Fournisseur> provider = fournisseurRepository.findByUserId(company.getUser().getId());
		if(provider.isPresent()) {
			throw new RecordIsAlreadyExist("You Are Already Provider");
		}
		Optional<Fournisseur> fournisseur = fournisseurRepository.findByCode(code);
		if(fournisseur.isPresent()) {
			throw new RecordIsAlreadyExist("This {code} is already found Please Try another Code");
		}
		
		Fournisseur meProvider = new Fournisseur();
		meProvider.setCode(code);
		meProvider.setName(company.getName());
		meProvider.setAddress(company.getAddress());
		meProvider.setCredit((double)0);
		meProvider.setEmail(company.getEmail());
		meProvider.setMvt((double)0);
		meProvider.setNature("personne Moral");
		meProvider.setPhone(company.getPhone());
		Set<Company> companies = new HashSet<>();
		companies.add(company);
		meProvider.setCompanies(companies);
		meProvider.setUser(company.getUser());
		fournisseurRepository.save(meProvider);
	}


	public List<FournisseurDto> getAllFournisseurHasUserId() {
		List<Fournisseur> fournisseurs = fournisseurRepository.findAllHasUserId();
		if(fournisseurs == null) {
			throw new RecordNotFoundException("There Is No Fournisseur Yet");
		}
		List<FournisseurDto> fournisseursDto = new ArrayList<>();
		for(Fournisseur i : fournisseurs) {
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(i);
			fournisseursDto.add(fournisseurDto);
		}
		return fournisseursDto;
	}


	public List<FournisseurDto> getAllMyVirtaul(Company company) {
		List<Fournisseur> fournisseurs = fournisseurRepository.findAllMyVirtual(company.getId());
		if(fournisseurs ==null) {
			return null;
		}
		List<FournisseurDto> fournisseursDto = new ArrayList<>();
		for (Fournisseur i : fournisseurs) {
			FournisseurDto fournisseurDto = fournisseurMapper.mapToDto(i);
			fournisseursDto.add(fournisseurDto);
		}
		return fournisseursDto;
	}


	public boolean existsById(Long id) {
		boolean existProvider = fournisseurRepository.existsById(id);
		if(!existProvider) {
			throw new RecordNotFoundException("there is no Provider with id "+id);
		}
		return true;
	}


	public boolean existsMyProvider(Long companyId, Long providerId) {
		boolean existsMyProvider = fournisseurRepository.existsMyProvider(companyId, providerId);
		if(!existsMyProvider) {
			throw new RecordNotFoundException("Please get add this Provider befor");
		}
		return true;
	}


	public Optional<Fournisseur> getMe(Long id) {
		Optional<Fournisseur> fournisseur = fournisseurRepository.findByUserId(id);
		return fournisseur;
	}


	public boolean existRelationBetweenProviderAndCompany(Long idProvider, Long id) {
		boolean exist = fournisseurRepository.existsMyProvider(id,idProvider);
		if(!exist) {
			throw new RecordNotFoundException("this provider is not your provider please send add request befor");
		}
		return true;
	}
	
	
}
