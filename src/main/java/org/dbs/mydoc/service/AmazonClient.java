package org.dbs.mydoc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class AmazonClient {

	private AmazonS3 amazonS3Client;

	@Value("${endPointUrl}")
	private String endPointUrl;

	@Value("${bucketName}")
	private String bucketName;

	@Value("${accessKey}")
	private String accessKey;

	@Value("${secretKey}")
	private String secretKey;

	public String uploadFile(MultipartFile multipartFile) {
		String fileUrl = "";
		try {
			File file = convertMutiPartToFile(multipartFile);

			String fileName = generateFileName(multipartFile);
			fileUrl = endPointUrl + "/" + bucketName + "/" + fileName;
			uploadFileToS3Bucket(fileName, file);
			
			
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileUrl;
	}

	public String deleteFile(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

		amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));

		return "success";
	}

	public InputStream downloadFile(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName + "/", fileName));
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		return inputStream;
	}

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.amazonS3Client = new AmazonS3Client(credentials);
	}

	private File convertMutiPartToFile(MultipartFile file) throws IOException {
		File newFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(newFile);
		fos.write(file.getBytes());
		fos.close();
		return newFile;
	}

	private String generateFileName(MultipartFile file) {
		return new Date().getTime() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
	}

	private void uploadFileToS3Bucket(String fileName, File file) {
	 amazonS3Client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
		
		
		}

}