package com.example.Farm.mapper;

import com.example.Farm.dto.request.PersonRequest;
import com.example.Farm.model.Person;

public class PersonMapper {
    
    private PersonMapper() {}

    public static void apply(PersonRequest request, Person person) {
        if (request == null || person == null) return;

        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
    }
}
