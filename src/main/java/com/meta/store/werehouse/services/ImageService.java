package com.meta.store.werehouse.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.werehouse.mapper.CompanyMapper;
import com.meta.store.werehouse.repository.CompanyRepository;

import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {


	private final ServletContext context;
	

	private final JwtAuthenticationFilter authenticationFilter;
	
	public String insertImag(MultipartFile file, String user,String service){
		boolean isExist = new File(context.getRealPath("/Images/"+service+"/")).exists();
		if(!isExist) {
			new File(context.getRealPath("/Images/"+service+"/")).mkdir();
		}
		
		String fileName = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(fileName)+"."+FilenameUtils.getExtension(fileName);
		File serverFile = new File (context.getRealPath("/Images/"+service+"/"+File.separator+user+File.separator+newFileName));
		try {
			FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return newFileName;
	}

	public byte[] getImage( String logo, String service, String name) throws IOException {
		if(name =="") {	
			System.out.println(name+"11111111");
		return Files.readAllBytes(Paths.get(context.getRealPath("/Images/"+service+"/"+authenticationFilter.userName+"/"+logo)));
		}
		System.out.println(name);
		return Files.readAllBytes(Paths.get(context.getRealPath("/Images/"+service+"/"+name+"/"+logo)));
		
	}
}
