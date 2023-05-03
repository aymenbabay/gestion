package com.meta.store.werehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.services.ArticleService;
import com.meta.store.werehouse.services.ClientService;
import com.meta.store.werehouse.services.InvoiceService;


@Component
public class WereHouseStarter implements CommandLineRunner {

	@Autowired
	private ArticleService articleService;
	
	@Autowired 
	private ClientService clientService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	private void insertArticle() {
		Article article = new Article("article 1","code1","01", "art1 is wondrfull",(double)4);
		articleService.insert(article);
	}
	
	private void insertClient() {
		Client client = new Client("aymen","code","aymen.babay@esprit.tn");
		clientService.insert(client);
		
	}
	
	private void insertInvoice() {

		
	}

	@Override
	public void run(String... args) throws Exception {
//		if(articleService.getById((long)1) == null) {
//		insertArticle();
//		insertClient();
//		insertInvoice();
//		}
		
	}
}
