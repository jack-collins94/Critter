package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.ConvertDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    @Autowired
    ConvertDTO convert;

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = convert.convertCustomerDTOToEntity(customerDTO);
        return convert.convertEntityToCustomerDTO(repository.save(customer));
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = repository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers) {
            customerDTOS.add(convert.convertEntityToCustomerDTO(customer));
        }

        return customerDTOS;
    }

    public CustomerDTO findOwnerByPetId(Long id) {
        Customer customer = repository.findAllByPetId(id);
        return convert.convertEntityToCustomerDTO(customer);
    }

    public Customer findCustomerById(Long id) {
        return repository.getOne(id);
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPet();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPet(pets);
        repository.save(customer);
    }
}
