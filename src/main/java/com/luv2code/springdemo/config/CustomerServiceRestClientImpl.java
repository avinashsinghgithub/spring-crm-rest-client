package com.luv2code.springdemo.config;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.luv2code.springdemo.entity.Customer;

@Service
public class CustomerServiceRestClientImpl implements CustomerService {

	private RestTemplate restTemplate;
	
	private String crmRestUrl; 
	
	private Logger logger = Logger.getLogger(getClass().getName());
	public CustomerServiceRestClientImpl(RestTemplate theRestTemplate,@Value("${crm.rest.url}") String theUrl) {
		restTemplate = theRestTemplate;
		crmRestUrl = theUrl;
		
		logger.info("Loaded Property: crm.rest.url="+crmRestUrl);
	}
	
	@Override
	public List<Customer> getCustomers(){
		logger.info("in getCustomers(): calling REST API "+crmRestUrl);
		// make REST call
		
		ResponseEntity<List<Customer>> responseEntity =
				restTemplate.exchange(crmRestUrl, HttpMethod.GET,null,new ParameterizedTypeReference<List<Customer>>() {});
		// get the list of customer from Response
		List<Customer> customers = responseEntity.getBody();
		
		logger.info("in getCustomers(): customers"+customers);
		return customers;
	}
	@Override
	public Customer getCustomer(int theId){
		logger.info("in getCustomer(): calling REST API "+theId);
		// make REST call
		
		Customer customer =
				restTemplate.getForObject(crmRestUrl+"/"+theId, Customer.class);
		// get the list of customer from Response
		//Customer customer = responseEntity.getBody();
		
		logger.info("in getCustomer(): customer"+customer);
		return customer;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		logger.info("in saveCustomer(): the customer being added "+theCustomer);
		
		int employeeId = theCustomer.getId();
		
		if(employeeId == 0) {
			// add an employee
			restTemplate.postForEntity(crmRestUrl,theCustomer,String.class);
		}else {
			
			// update employee
			restTemplate.put(crmRestUrl, theCustomer);
		}
		
		
		logger.info("in saveCustomer(): saved customer successfully");
		
	}

	@Override
	public void deleteCustomer(int theId) {

		logger.info("In deleteCustomer(): Calling REST API "+crmRestUrl);
		
		// make REST call
		restTemplate.delete(crmRestUrl+"/"+theId);
		
		logger.info("In deleteCustomer(): deleted customer theId="+theId);
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// TODO Auto-generated method stub
		logger.info("search string "+theSearchName);
		logger.info("Calling REST API "+crmRestUrl+"?theSearchName="+theSearchName);
		ResponseEntity<List<Customer>> responseEntity =
				restTemplate.exchange(crmRestUrl+"?theSearchName="+theSearchName, HttpMethod.GET,null,new ParameterizedTypeReference<List<Customer>>() {});
		// get the list of customer from Response
		List<Customer> customers = responseEntity.getBody();
		return customers;
	}
}
