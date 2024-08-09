package com.micode.system.infractionsSoap.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "infractions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Infraction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false, length = 8)
	private String dni;
	
	@Column(nullable = true, length = 255)
	private String description;
	
	@Column(nullable = false, length = 200)
	private String location;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date date;
	
	@Column(nullable = false, length = 20)
	private String infractionType;
	
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2)")
	private Double amount;
	
	@Column(nullable = false, length = 20)
	private String status;
}
