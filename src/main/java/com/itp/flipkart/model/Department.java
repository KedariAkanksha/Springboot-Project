package com.itp.flipkart.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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


public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deptId;

	@Column(nullable = false, length = 100)
	private String departmentName;

	@Column(length = 250)
	private String departmentCode;

	@Column(length = 250)
	private String description;

	@Column(nullable = false, length = 20)
	private String departmentType;

	@Column(nullable = false)
	private double budget;

	@Column(nullable = false, length = 10)
	private String status;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> employees = new ArrayList<>();

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
