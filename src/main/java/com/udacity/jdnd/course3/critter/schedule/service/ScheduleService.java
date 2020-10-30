package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.ConvertDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository repository;

    @Autowired
    ConvertDTO convertDTO;

    @Autowired
    PetService petService;

    public ScheduleDTO create(ScheduleDTO scheduleDTO) {
        return convertDTO.convertEntityToScheduleDTO(repository.save(convertDTO.convertScheduleDTOToEntity(scheduleDTO)));

    }

    public List<ScheduleDTO> findAll() {
        List<Schedule> schedules = repository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByPetId(Long petId) {
        List<Schedule> schedules = repository.findByPets_Id(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }

        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByEmployeeId(Long employeeId) {
        List<Schedule> schedules = repository.findByEmployees_Id(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertDTO.convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    public List<ScheduleDTO> findByCustomerId(long customerId) {
        List<Pet> pets = petService.getPetByCustomerId(customerId);
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        pets.forEach(pet -> {
            List<ScheduleDTO> petsSchedulesDTO = findByPetId(pet.getId());
            schedulesDTO.addAll(petsSchedulesDTO);
        });

        for (Pet pet : pets) {
            schedulesDTO.addAll(findByPetId(pet.getId()));
        }

        return schedulesDTO;
    }
}
