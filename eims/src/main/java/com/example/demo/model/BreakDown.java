package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Entity
@Data
public class BreakDown implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "break_down_id")
	private Long breakDownId;
	
	@Column(name = "item")
	private String productName;
	
	@Column(name = "cost")
	private float price;
	
	private int quantity;
	
	private float total;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "finance_id")
	private Finance finance;
	
}
