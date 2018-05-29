package org.dbs.mydoc.controller;

import org.dbs.mydoc.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public class StorageController {

	@Autowired
    private AmazonClient amazonClient;
	
	@PostMapping("/upload")
	public String uploadFile(@RequestPart(value="file")MultipartFile file) 
	{
		
		
		return null;
		
	}
	
}
