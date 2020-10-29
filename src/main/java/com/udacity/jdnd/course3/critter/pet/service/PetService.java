package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.dto.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.ConvertDTO;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository repository;

    @Autowired
    ConvertDTO convertDTO;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;


    public PetDTO save(PetDTO petDTO){
        Pet pet = repository.save(convertDTO.convertPetDTOToEntity(petDTO));
        customerService.addPetToCustomer(pet, pet.getCustomer());
        return convertDTO.convertEntityToPetDTO(pet);

    }

    public PetDTO findPetById(Long id){
        return convertDTO.convertEntityToPetDTO(repository.getOne(id));
    }

    public List<PetDTO> getAllPets(){
        List<Pet> pets = repository.findAll();
        List<PetDTO> petDTOS = new ArrayList<>();
        for (Pet pet:pets) {
            petDTOS.add(convertDTO.convertEntityToPetDTO(pet));
        }
        return petDTOS;
    }

    public List<PetDTO> getPetByOwnerId(Long id){
        List<Pet> pets = repository.findAllByCustomerId(id);
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet: pets){
            petDTOS.add(convertDTO.convertEntityToPetDTO(pet));
        }
        return petDTOS;
    }
}
