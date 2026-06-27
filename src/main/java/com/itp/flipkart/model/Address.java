package com.itp.flipkart.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder


public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;

	@Column(nullable = false, length = 100)
	private String locality;

	@Column(nullable = false, length = 50)
	private String city;

	@Column(nullable = false, length = 50)
	private String state;

	@Column(nullable = false, length = 50)
	private String country;

	@Column(nullable = false, length = 6)
	private String zipCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeid")
	private Employee employee;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private LocalDateTime modifiedAt;

	@PrePersist
	protected void atCreation() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.modifiedAt = now;
	}

	@PreUpdate
	protected void atUpdation() {
		this.modifiedAt = LocalDateTime.now();
	}
}
