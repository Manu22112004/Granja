package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.CustomerRequest;
import com.example.Farm.dto.response.CustomerResponse;
import com.example.Farm.model.Customer;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerResponse toResponse(Customer customer) {
        if (customer == null) return null;

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .contactInfo(customer.getContactInfo())
                .active(customer.getActive())
                .build();
    }

    public static List<CustomerResponse> toResponseList(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) return List.of();
        return customers.stream().map(CustomerMapper::toResponse).toList();
    }

    public static Customer toEntity(CustomerRequest request) {
        if (request == null) return null;
        Customer customer = new Customer();
        apply(request, customer);
        return customer;
    }

    public static void copyToEntity(CustomerRequest request, Customer entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(CustomerRequest request, Customer customer) {
        if (request.getName() != null) customer.setName(request.getName());
        if (request.getContactInfo() != null) customer.setContactInfo(request.getContactInfo());
        if (request.getActive() != null) customer.setActive(request.getActive());
    }
}
