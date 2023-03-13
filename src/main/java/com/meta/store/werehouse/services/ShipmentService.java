package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.mapper.ShipmentMapper;
import com.meta.store.werehouse.repository.ShipmentRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentService extends BaseService<Shipment, Long> {
	
	
	private final ShipmentMapper shipmentMapper;
	
	private final ShipmentRepository shipmentRepository;
	
	public ResponseEntity<ShipmentDto> upDateShipment( ShipmentDto shipmentDto, Company company) {
		Optional<Shipment> shipment = shipmentRepository.findByIdAndCompanyId(shipmentDto.getId(),company.getId());
		if(shipment.isPresent()) {
			Shipment categ = shipmentMapper.mapToEntity(shipmentDto);
			categ.setCompany(company);
			shipmentRepository.save(categ);
			return ResponseEntity.ok(shipmentDto);
			
		}else {
			throw new RecordNotFoundException("Shipment Not Found");
		}
	}

	public Optional<Shipment> getByCode(String code, Long companyId) {
		return shipmentRepository.findByCodeAndCompanyId(code, companyId);
	}

	public List<Shipment> getAllByCompanyId(Long companyId) {
		return shipmentRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Shipment> getByCodeAndCompanyId(String code, Long companyId) {
		Optional<Shipment> categ = shipmentRepository.findByCodeAndCompanyId(code,companyId);
		if(!categ.isEmpty()) {
		Shipment shipment = categ.get();
		return ResponseEntity.ok(shipment);
		}
		else return null;
	}
	
	public Optional<Shipment> getByIdAndCompanyId(Long id , Long companyId) {
		return shipmentRepository.findByIdAndCompanyId(id, companyId);
	}
	

}
