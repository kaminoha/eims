package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Data
public class Finance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "finance_id")
	private Long financeId;

	@Column(name = "assets")
	private int assets;
	
	@Column(name = "profit")
	private int profit;
	
	@Column(name = "loss")
	private int loss;
	
	@Column(name = "revenue")
	private int revenue;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "f_date")
	private Date fDate;
	
	@Column(name = "currency")
	private String currency;

	@OneToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "finance" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BreakDown> breakDown;
}
