package com.hieu.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hieu.blog.service.FileService;

/**
 * @author LeChiHieu
 * 
 *         the class FileServiceImpl is implement of FileService interface,
 *         where handle logic of methods of the FileService interface
 *
 */

@Service
public class FileServiceImpl implements FileService {

//	private final Path fileStoreLocation;

	/**
	 * the method String uploadImage(String path, MultipartFile file) which override
	 * method of FileService interface
	 *
	 * which upload image of post
	 */

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// this.fileStoreLocation =Paths.get(file.get)

		// get path name
		String name = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// get full path
		String filePath = path + File.separator + fileName;

		File f = new File(path);

		/**
		 * check file exits
		 */
		if (!f.exists()) {

			f.mkdir();
		}

		/**
		 * using data inputStream to get path URL
		 */
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName;
	}

	/**
	 * the method InputStream getResource(String path, String fileName) override
	 * method getResource(String path, String fileName) of FileService interface
	 * 
	 * get resource of file
	 * 
	 * which get resource includes parameters:
	 * 
	 * @Parameter String path is path of file
	 * 
	 * @Parameter String fileName is name file
	 * 
	 * @return InputStream data
	 */

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
