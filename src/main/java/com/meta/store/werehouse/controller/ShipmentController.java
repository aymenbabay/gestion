package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.mapper.ShipmentMapper;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.ShipmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/shipment")
@RequiredArgsConstructor
public class ShipmentController {
	private final ShipmentMapper shipmentMapper;
	
	private final ShipmentService shipmentService;
	
	private final BaseService<Shipment, Long> baseService;
		

	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<ShipmentDto>> getShipmentByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<Shipment> shipments = shipmentService.getAllByCompanyId(companyId);
		if(!shipments.isEmpty()) {
		List<ShipmentDto> shipmentsDto = new ArrayList<>();
		for(Shipment i : shipments) {
			ShipmentDto shipmentDto = shipmentMapper.mapToDto(i);
			shipmentsDto.add(shipmentDto);
		}
		return ResponseEntity.ok(shipmentsDto);}
		throw new RecordNotFoundException("there is no Shipment");
	}
	
	@GetMapping("/l/{code}")
	public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable String code){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<Shipment> shipment = shipmentService.getByCodeAndCompanyId(code,company.getId());
		if(shipment != null) {
		ShipmentDto dto = shipmentMapper.mapToDto(shipment.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No Shipment With Libelle : "+code);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<ShipmentDto> insertShipment(@RequestBody @Valid ShipmentDto shipmentDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		ResponseEntity<Shipment> shipment1 = shipmentService.getByCodeAndCompanyId(shipmentDto.getCode(),company.getId());
		if(shipment1 == null)  {
		Shipment shipment = shipmentMapper.mapToEntity(shipmentDto);
		shipment.setCompany(company);
		baseService.insert(shipment);
		return new ResponseEntity<ShipmentDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<ShipmentDto> upDateShipment(@RequestBody @Valid ShipmentDto shipmentDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return shipmentService.upDateShipment(shipmentDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/{id}")
	public void deleteShipmentById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<Shipment> shipment = shipmentService.getByIdAndCompanyId(id,company.getId());
			if(shipment.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This Shipment Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
}
