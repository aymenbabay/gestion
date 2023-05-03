package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.dto.CommandLineDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.CommandLine;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.mapper.CategoryMapper;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.repository.CategoryRepository;
import com.meta.store.werehouse.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService extends BaseService<Inventory, Long> {

	private final InventoryMapper inventoryMapper;
	
	private final InventoryRepository inventoryRepository;
	

	public List<InventoryDto> getInventoryByCompanyId(Long companyId) {
		List<Inventory> inventory = inventoryRepository.findByCompanyId(companyId);
		if(inventory == null) {
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need");
	}
			List<InventoryDto> inventoriesDto = new ArrayList<>();
			for(Inventory i:inventory) {
		InventoryDto inventoryDto = inventoryMapper.mapToDto(i);
		inventoriesDto.add(inventoryDto);
			}
			return inventoriesDto;
	}
	
	public ResponseEntity<InventoryDto> makeInventory(Article articleDto, Company company){
		Inventory inventory = new Inventory();
		inventory.setArticleCost(articleDto.getCost() * articleDto.getQuantity());
		inventory.setArticleSelling((double)0);
		inventory.setLibelle_article(articleDto.getLibelle());
		inventory.setCurrent_quantity(articleDto.getQuantity());
		inventory.setIn_quantity(articleDto.getQuantity());
		inventory.setCompany(company);
		inventory.setArticleCode(articleDto.getCode());
		inventory.setOut_quantity((double)0);
		inventoryRepository.save(inventory);
		return null;
		 
	}

	public Optional<Inventory> getArticleByCode(String codeArticle, Long companyId) {
		return inventoryRepository.findByCompanyIdAndArticleCode(companyId,codeArticle);
	}

	public void addQuantity(Article article, Long companyId, Long quantity) {
		Optional<Inventory> inventori = inventoryRepository.findByCompanyIdAndArticleCode(companyId,article.getCode());
		Inventory inventory = inventori.get();
		inventory.setIn_quantity(inventory.getIn_quantity()+quantity);
		inventory.setCurrent_quantity(inventory.getCurrent_quantity()+quantity);
		inventory.setArticleCost(inventory.getArticleCost() + article.getCost()* quantity);
		inventoryRepository.save(inventory);
		
	}

	public void deleteByArticleCode(String code, Long id2) {
		inventoryRepository.deleteByCompanyIdAndArticleCode(id2,code);
		
	}

	public void updateArticle(Article art, Company company, Double deference) {
		Optional<Inventory> inventori = inventoryRepository.findByCompanyIdAndArticleCode(company.getId(),art.getCode());
		Inventory inventory = inventori.get();
		if(deference!=0) {
			inventory.setCurrent_quantity(inventory.getCurrent_quantity()-deference);
			inventory.setIn_quantity(inventory.getIn_quantity()-deference);
			inventory.setArticleCost(inventory.getArticleCost() +art.getCost() * deference);
		}
		inventory.setLibelle_article(art.getLibelle());
		inventoryRepository.save(inventory);
	}

	public void impacteInvoice( Company company, List<CommandLineDto> commandLinesDto, List<Article> articles) {
		for(CommandLineDto i : commandLinesDto) {
			
		Optional<Inventory> inventory = inventoryRepository.findByCompanyIdAndArticleCode(company.getId(),i.getCodeArticle());
		Inventory inventori = inventory.get();
		inventori.setOut_quantity(inventori.getOut_quantity()+i.getQuantity());
		inventori.setCurrent_quantity(inventori.getCurrent_quantity()-i.getQuantity());
		for(Article a : articles) {
			if(a.getCode().equals(i.getCodeArticle())) {
				
		inventori.setArticleSelling(inventori.getArticleSelling() + a.getCost() * i.getQuantity());
			}
		}
		
		inventoryRepository.save(inventori);
		}
		
	}

}
