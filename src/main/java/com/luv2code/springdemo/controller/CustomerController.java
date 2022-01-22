package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.config.CustomerService;
import com.luv2code.springdemo.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel,@RequestParam(name = "sortBy",required=false) String sort) { 
		
		
		List<Customer>customers = customerService.getCustomers();
		
		// add the customers to the model
		theModel.addAttribute("customers",customers);
		return "list-customers";
	}
	@GetMapping("showFormForUpdate")
	public String getCustomer(Model theModel,@RequestParam int customerId) { 
		
		Customer customer = customerService.getCustomer(customerId);
		
		// add the customers to the model
		theModel.addAttribute("customer",customer);
		return "customer-form";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer newCustomer = new Customer();
		theModel.addAttribute("customer",newCustomer);
		return "customer-form";
	}
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer")Customer theCustomer) {
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
//	
//	@GetMapping("/showFormForUpdate")
//	public String showFormForUpdate(@RequestParam("customerId")int theId,Model theModel) {
//		
//		// get the customer from the customer service 
//		Customer theCustomer = customerService.getCustomer(theId);
//		// add the customer in the model and an attribute
//		theModel.addAttribute("customer",theCustomer);
//		// send over to our form
//		return "customer-form";
//	}
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId")int theId,Model theModel) {
		
		// get the customer from the customer service 
		customerService.deleteCustomer(theId);
		
		// redirect to the customer list page
		return "redirect:/customer/list";
	}
	 @GetMapping("/search")
	    public String searchCustomers(@RequestParam(required=false) String theSearchName,
	                                    Model theModel) {
	        // search customers from the service
	        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
	                
	        // add the customers to the model
	        theModel.addAttribute("customers", theCustomers);
	        return "list-customers";        
	    }
}
