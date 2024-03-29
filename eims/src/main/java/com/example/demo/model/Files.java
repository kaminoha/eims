package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Data
public class Files implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id    
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String fileId;
	
	private String fileName;
	
	private String fileType;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] fileData;
	
	public Files() {
		
	}
	
	public Files(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType= fileType;
		this.fileData = data;
	}

}
