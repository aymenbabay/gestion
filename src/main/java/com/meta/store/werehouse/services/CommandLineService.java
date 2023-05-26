package com.meta.store.werehouse.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CommandLineDto;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.CommandLine;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.mapper.CommandLineMapper;
import com.meta.store.werehouse.mapper.InvoiceMapper;
import com.meta.store.werehouse.repository.CommandLineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandLineService extends BaseService<CommandLine, Long>{
	
	
	private final CommandLineMapper commandLineMapper;
	
	private final InvoiceMapper invoiceMapper;

	private final InvoiceService invoiceService;
	
	private final ArticleService articleService;
	
	private final InventoryService inventoryService;
	
	private final CommandLineRepository commandLineRepository;
	

    DecimalFormat df = new DecimalFormat("#.###");

	public void insertLine(List<CommandLineDto> commandLinesDto, Company company ) {
		List<CommandLine> commandLines = new ArrayList<>();
		List<Article> articles = new ArrayList<>();
		InvoiceDto invoiceDto = invoiceService.getLastInvoice(company.getId());
		Invoice invoice = invoiceMapper.mapToEntity(invoiceDto);
		
		for(CommandLineDto i : commandLinesDto) {
		//	Article article = articleService.findByCodeAndCompanyId(i.getCodeArticle(),company);
			articles.add(i.getArticle());
			if(i.getArticle().getQuantity()-i.getQuantity()<0) {
				throw new RecordNotFoundException("There Is No More "+i.getArticle().getLibelle());
			}
			i.getArticle().setQuantity(i.getArticle().getQuantity()-i.getQuantity());
			articleService.insert(i.getArticle());
		CommandLine commandLine = commandLineMapper.mapToEntity(i);
		commandLine.setInvoice(invoice);
		String prix_article_tot = df.format(i.getQuantity()*i.getArticle().getSellingPrice());
		prix_article_tot = prix_article_tot.replace(",", ".");
		commandLine.setPrix_article_tot(Double.parseDouble(prix_article_tot));
	//	commandLine.setPrix_article_unit(i.getArticle().getSellingPrice());
	//	commandLine.setTva(article.getTva());
		String tot_tva = df.format(i.getArticle().getTva()*i.getQuantity()*i.getArticle().getSellingPrice()/100);
		tot_tva = tot_tva.replace(",", ".");
		commandLine.setTot_tva(Double.parseDouble(tot_tva));
	//	commandLine.setUnit(article.getUnit());
	//	commandLine.setLibelle_article(article.getLibelle());
	//	commandLine.setCode_article(article.getCode());
		commandLines.add(commandLine);
		}
		super.insertAll(commandLines);
		List<CommandLine> commandLine = commandLineRepository.findByInvoiceCode(invoice.getCode());
		double totHt= 0;
		double totTva= 0;
		double totTtc= 0;
		for(CommandLine i : commandLine) {
			 totHt =+ i.getPrix_article_tot();
			 totTva =+ i.getTot_tva();
			 totTtc =+ totHt+totTva;
		}
		invoice.setPrix_tot_article(totHt);
		invoice.setTot_tva_invoice(totTva);
		invoice.setPrix_invoice_tot(totTtc);
		invoiceService.insert(invoice);
		inventoryService.impacteInvoice(company,commandLinesDto,articles);
		
	}


	public void deleteByInvoiceId(Long id) {
		commandLineRepository.deleteByInvoiceId(id);
		
	}

	public List<CommandLine> getCommandLineByInvoiceId(Long id){
		return commandLineRepository.findByInvoiceId(id);
	}
}
