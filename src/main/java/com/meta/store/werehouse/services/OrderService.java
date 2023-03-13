package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.mapper.OrderMapper;
import com.meta.store.werehouse.repository.InventoryRepository;
import com.meta.store.werehouse.repository.OrderRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService extends BaseService<Order, Long> {
	
	private final OrderMapper orderMapper;
	
	private final OrderRepository orderRepository;
	
	public ResponseEntity<OrderDto> upDateOrder( OrderDto orderDto, Company company) {
		Optional<Order> order = orderRepository.findByIdAndCompanyId(orderDto.getId(),company.getId());
		if(order.isPresent()) {
			Order categ = orderMapper.mapToEntity(orderDto);
			categ.setCompany(company);
			orderRepository.save(categ);
			return ResponseEntity.ok(orderDto);
			
		}else {
			throw new RecordNotFoundException("Order Not Found");
		}
	}

	public Optional<Order> getByCode(Long code, Long companyId) {
		return orderRepository.findByCodeAndCompanyId(code, companyId);
	}

	public List<Order> getAllByCompanyId(Long companyId) {
		return orderRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Order> getByCodeAndCompanyId(Long code, Long companyId) {
		Optional<Order> categ = orderRepository.findByCodeAndCompanyId(code,companyId);
		if(!categ.isEmpty()) {
		Order order = categ.get();
		return ResponseEntity.ok(order);
		}
		else return null;
	}
	
	public Optional<Order> getByIdAndCompanyId(Long id , Long companyId) {
		return orderRepository.findByIdAndCompanyId(id, companyId);
	}
	
}
