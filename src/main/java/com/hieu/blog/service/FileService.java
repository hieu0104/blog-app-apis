package com.hieu.blog.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LeChiHieu
 * 
 * the class is service player of File class , contains method  name of FileService class 
 *
 */
public interface FileService {
	
String uploadImage (String path ,MultipartFile file) throws IOException;

InputStream getResource (String path , String fileName) throws FileNotFoundException;

}
