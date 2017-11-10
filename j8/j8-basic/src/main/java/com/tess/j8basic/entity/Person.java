package com.tess.j8basic.entity;

import javax.persistence.Entity;

@Entity
public class Person {

	private String name;
	
	public Person() {}
	
	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
	
	
}
