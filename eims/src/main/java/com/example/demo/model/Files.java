package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Files implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id    
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String fileId;
	
	private String fileName;
	
	private String fileType;
	
	@Lob
	private byte[] fileData;
	
	public Files() {
		
	}
	
	public Files(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType= fileType;
		this.fileData = data;
	}

	public String getId() {
		return fileId;
	}

	public void setId(String id) {
		this.fileId = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return fileData;
	}

	public void setData(byte[] data) {
		this.fileData = data;
	}

}
