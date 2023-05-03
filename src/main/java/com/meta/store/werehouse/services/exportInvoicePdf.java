package com.meta.store.werehouse.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.CommandLine;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Invoice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class exportInvoicePdf {

	private final CommandLineService commandLineService;
	

	public static ByteArrayInputStream invoicePdf(List<CommandLine> commandLines, InvoiceDto invoiceDto, Company company)  {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
		
		document.open();
		
		// add text to pdf file
		Font font = FontFactory.getFont(FontFactory.COURIER,14, BaseColor.BLACK);
				
		Paragraph ste = new Paragraph("ste: ", font);
		ste.add(company.getName());
		ste.setAlignment(Element.ALIGN_LEFT);
		Paragraph secteur = new Paragraph("secteur: ", font);
		secteur.add(company.getIndestrySector());
		secteur.setAlignment(Element.ALIGN_RIGHT);
		ste.add(secteur);
		document.add(ste);
		
		Paragraph phone = new Paragraph("phone: ", font);
		phone.add(company.getPhone());
		phone.setAlignment(Element.ALIGN_LEFT);
		document.add(phone);
		
		Paragraph email = new Paragraph("email: ", font);
		email.add(company.getEmail());
		email.setAlignment(Element.ALIGN_RIGHT);
		document.add(email);
		
		Paragraph address = new Paragraph("address ", font);
		address.add(company.getAddress());
		address.setAlignment(Element.ALIGN_LEFT);
		document.add(address);

		Paragraph date = new Paragraph("date facture ", font);
		LocalDateTime lastModifiedDate = invoiceDto.getLastModifiedDate();
		Date dateObj = Date.from(lastModifiedDate.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(dateObj);
		date.add(formattedDate);
		date.setAlignment(Element.ALIGN_RIGHT);
		document.add(date);



		
		Paragraph para = new Paragraph("Facture N°: ", font);
		para.add(invoiceDto.getCode().toString());
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
		
		Paragraph client = new Paragraph("Client: ", font);
		client.setAlignment(Element.ALIGN_LEFT);
		document.add(client);
		
		Paragraph name = new Paragraph("name: ", font);
		name.add(invoiceDto.getClient().getName());
		name.setAlignment(Element.ALIGN_LEFT);
		document.add(name);

		Paragraph addressclient = new Paragraph("Address: ", font);
		addressclient.add(invoiceDto.getClient().getAddress());
		addressclient.setAlignment(Element.ALIGN_LEFT);
		document.add(addressclient);

		Paragraph phoneclient = new Paragraph("phone: ", font);
		phoneclient.add(invoiceDto.getClient().getPhone());
		phoneclient.setAlignment(Element.ALIGN_LEFT);
		document.add(phoneclient);
		document.add(Chunk.NEWLINE);
		
		PdfPTable table = new PdfPTable(7);
		
		// make column title
		
		Stream.of("Libelle","Qte","Unit","Tva","Prix Unit","Tot Tva","Prix Tot Article").forEach(headerTitle ->{
			PdfPCell header = new PdfPCell();
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setBorderWidth(1);
			header.setPhrase(new Phrase(headerTitle, headFont));
			table.addCell(header);
		});
		
		for(CommandLine i : commandLines) {
			
		PdfPCell libelleCell = new PdfPCell(new Phrase(i.getLibelle_article()));
		libelleCell.setPaddingLeft(1);
		libelleCell.setVerticalAlignment(Element.ALIGN_CENTER);
		libelleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(libelleCell);


		PdfPCell qteCell = new PdfPCell(new Phrase(i.getQuantity().toString()));
		qteCell.setPaddingLeft(1);
		qteCell.setVerticalAlignment(Element.ALIGN_CENTER);
		qteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(qteCell);
		
		PdfPCell unitCell = new PdfPCell(new Phrase(i.getUnit()));
		unitCell.setPaddingLeft(1);
		unitCell.setVerticalAlignment(Element.ALIGN_CENTER);
		unitCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(unitCell);
		
		PdfPCell tvaCell = new PdfPCell(new Phrase(i.getTva().toString()));
		tvaCell.setPaddingLeft(1);
		tvaCell.setVerticalAlignment(Element.ALIGN_CENTER);
		tvaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(tvaCell);
		
		PdfPCell puCell = new PdfPCell(new Phrase(i.getPrix_article_unit().toString()));
		puCell.setPaddingLeft(1);
		puCell.setVerticalAlignment(Element.ALIGN_CENTER);
		puCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(puCell);
		
		PdfPCell tottvaCell = new PdfPCell(new Phrase(i.getTot_tva().toString()));
		tottvaCell.setPaddingLeft(1);
		tottvaCell.setVerticalAlignment(Element.ALIGN_CENTER);
		tottvaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(tottvaCell);
		
		PdfPCell prixtotCell = new PdfPCell(new Phrase(i.getPrix_article_tot().toString()));
		prixtotCell.setPaddingLeft(1);
		prixtotCell.setVerticalAlignment(Element.ALIGN_CENTER);
		prixtotCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(prixtotCell);
		}
		
		
		PdfPTable totalTable = new PdfPTable(3);
		

		totalTable.setWidthPercentage(30);
		totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.getDefaultCell().setBorderWidth(0);

		PdfPCell emptyCell = new PdfPCell(new Phrase("Total HT:"));
		emptyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(emptyCell);

		PdfPCell totalHTCell = new PdfPCell(new Phrase(invoiceDto.getPrix_tot_article().toString()));
		totalHTCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(totalHTCell);

		PdfPCell e = new PdfPCell(new Phrase(""));
		e.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(e);

		PdfPCell totalHTValueCell = new PdfPCell(new Phrase("Total TVA:"));
		totalHTValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(totalHTValueCell);

		PdfPCell totalTVACell = new PdfPCell(new Phrase(invoiceDto.getTot_tva_invoice().toString()));
		totalTVACell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(totalTVACell);

		PdfPCell a = new PdfPCell(new Phrase(""));
		a.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(a);
		
		PdfPCell totalTVAValueCell = new PdfPCell(new Phrase("Total TTC:"));
		totalTVAValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(totalTVAValueCell);

		PdfPCell totalTTCCell = new PdfPCell(new Phrase(invoiceDto.getPrix_invoice_tot().toString()));
		totalTTCCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(totalTTCCell);
		
		PdfPCell b = new PdfPCell(new Phrase(""));
		b.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTable.addCell(b);

		
		document.add(table);
		document.add(totalTable);
		document.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  new ByteArrayInputStream(out.toByteArray());
	}
}
