package com.ssafy.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Subway {
	@Id
	@Column(name = "id")
	int id;
	
	String name;
	String line;
	double latitude;
	double longitude;
}
