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
import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.mapper.InvoiceMapper;
import com.meta.store.werehouse.mapper.OrderMapper;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.InvoiceService;
import com.meta.store.werehouse.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderMapper orderMapper;
	
	private final OrderService orderService;
	
	private final BaseService<Order, Long> baseService;
		
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<OrderDto>> getOrderByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<Order> orders = orderService.getAllByCompanyId(companyId);
		if(!orders.isEmpty()) {
		List<OrderDto> ordersDto = new ArrayList<>();
		for(Order i : orders) {
			OrderDto orderDto = orderMapper.mapToDto(i);
			ordersDto.add(orderDto);
		}
		return ResponseEntity.ok(ordersDto);}
		throw new RecordNotFoundException("there is no order");
	}
	
	@GetMapping("/l/{code}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long code){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<Order> order = orderService.getByCodeAndCompanyId(code,company.getId());
		if(order != null) {
		OrderDto dto = orderMapper.mapToDto(order.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No Order With Libelle : "+code);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<OrderDto> insertOrder(@RequestBody @Valid OrderDto orderDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		ResponseEntity<Order> order1 = orderService.getByCodeAndCompanyId(orderDto.getCode(),company.getId());
		if(order1 == null)  {
		Order order = orderMapper.mapToEntity(orderDto);
		order.setCompany(company);
		baseService.insert(order);
		return new ResponseEntity<OrderDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<OrderDto> upDateOrder(@RequestBody @Valid OrderDto orderDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return orderService.upDateOrder(orderDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrderById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<Order> order = orderService.getByIdAndCompanyId(id,company.getId());
			if(order.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This Order Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
}
