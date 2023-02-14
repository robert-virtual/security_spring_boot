package com.example.security.controller;

import com.example.security.model.Customer;
import com.example.security.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
   private CustomerRepository customerRepo;
    @GetMapping("/all")
    private Iterable<Customer> allCustomers(){
        return customerRepo.findAll();
    }


}
