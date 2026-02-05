package com.example.Farm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Farm.dto.request.CustomerRequest;
import com.example.Farm.dto.response.CustomerResponse;
import com.example.Farm.exception.ResourceNotFoundException;
import com.example.Farm.mapper.CustomerMapper;
import com.example.Farm.model.Customer;
import com.example.Farm.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> getAll() {
        return CustomerMapper.toResponseList(customerRepository.findAll());
    }

    public CustomerResponse getById(UUID id) {
        return CustomerMapper.toResponse(findCustomerOrThrow(id));
    }

    public CustomerResponse create(CustomerRequest req) {
        Customer customer = CustomerMapper.toEntity(req);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    public CustomerResponse update(UUID id, CustomerRequest req) {
        Customer customer = findCustomerOrThrow(id);
        CustomerMapper.copyToEntity(req, customer);
        return CustomerMapper.toResponse(customer);
    }

    public void deactivate(UUID id) {
        Customer customer = findCustomerOrThrow(id);
        customer.setActive(false);
    }

    private Customer findCustomerOrThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
}
