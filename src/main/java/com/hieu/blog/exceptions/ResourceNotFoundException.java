package com.hieu.blog.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fileName;
	long fileValue;

	public ResourceNotFoundException(String resourceName, String fileName, long fileValue) {
		super(String.format("%s Not found with %s :%s", resourceName, fileName, fileValue));
		
		this.resourceName = resourceName;
		this.fileName = fileName;
		this.fileValue = fileValue;
	}

}
