package com.luv2code.springdemo.config;

import java.util.List;

import com.luv2code.springdemo.entity.Customer;

public interface CustomerService {

	List<Customer> getCustomers();

	Customer getCustomer(int theId);

	void saveCustomer(Customer theCustomer);

	void deleteCustomer(int theId);

	List<Customer> searchCustomers(String theSearchName);

}
