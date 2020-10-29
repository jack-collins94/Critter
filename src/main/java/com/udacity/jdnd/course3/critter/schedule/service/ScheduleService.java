package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.ConvertDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository repository;

    @Autowired
    ConvertDTO convertDTO;

    @Autowired
    CustomerService customerService;

    public ScheduleDTO create(ScheduleDTO scheduleDTO){
        Schedule schedule = convertDTO.convertScheduleDTOToEntity(scheduleDTO);
        schedule = repository.save(schedule);
        return convertDTO.convertEntityToScheduleDTO(schedule);
    }

    public List<ScheduleDTO> findAll(){
        List<Schedule> schedules = repository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByPetId(long id){
        List<Schedule> schedules = repository.findAllByPetsId(id);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByEmployeeId(long id) {
        List<Schedule> schedules = repository.findAllByEmployeesId(id);

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByCustomerId(long customerId) {
        Customer customer  = customerService.findCustomerById(customerId);
        List<Pet> customerPets = customer.getPet();
        List<Long> petIds = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Pet pet:customerPets){
            petIds.add(pet.getId());
        }

        for (Long id:petIds){
            schedules.addAll(repository.findAllByPetsId(id));
        }
        for(Schedule schedule:schedules){
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;

    }

}
