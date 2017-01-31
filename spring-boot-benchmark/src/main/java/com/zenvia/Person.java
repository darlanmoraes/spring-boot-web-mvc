package com.zenvia;

public class Person {

	private String name;
	private Integer age;
	private Integer weight;
	private String address;

	public Person() {
	}

	public Person(String name, Integer age, Integer weight, String address) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
