package com.meta.store.werehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

	@NotBlank(message = "Libelle field must not be empty")
	private String libelle;
	
	@NotBlank(message = "Code Field Must not Be Empty")
	private String code;

	private String ref;
	
	private String discription;
	
	private Double prix;
}
