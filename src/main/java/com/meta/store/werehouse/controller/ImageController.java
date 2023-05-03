package com.meta.store.werehouse.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.services.ImageService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	
	@GetMapping(path = "/{lien}/{service}")
	public byte[] getImage( @PathVariable String lien, @PathVariable String service)throws Exception {
		return imageService.getImage( lien,service);
				}
}
