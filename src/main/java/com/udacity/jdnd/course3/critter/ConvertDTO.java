package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.dto.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.entity.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ConvertDTO {

    public CustomerDTO convertEntityToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPet() != null) {
            customer.getPet().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Customer convertCustomerDTOToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPet(new ArrayList<>());
        if(customerDTO.getPetIds() != null && customerDTO.getPetIds().size() > 0) {
            customerDTO.getPetIds().forEach(id -> {
                Pet pet = new Pet();
                pet.setId(id);
                customer.getPet().add(pet);
            });
        }
        return customer;
    }

    public Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        if (employeeDTO.getSkills() != null && employeeDTO.getSkills().size() > 0) {
            Set<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());
            employee.setSkills(skills);
        }

        if (employeeDTO.getDaysAvailable() != null && employeeDTO.getDaysAvailable().size() > 0) {
            Set<DayOfWeek> availability = new HashSet<>(employeeDTO.getDaysAvailable());
            employee.setDaysAvailable(availability);
        }

        return employee;
    }

    public EmployeeDTO convertEntityToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        if(employee.getSkills() != null && employee.getSkills().size() > 0) {
            Set<EmployeeSkill> skills = new HashSet<>(employee.getSkills());
            employeeDTO.setSkills(skills);
        }

        if(employee.getDaysAvailable() != null && employee.getDaysAvailable().size() > 0) {
            Set<DayOfWeek> availability = new HashSet<>(employee.getDaysAvailable());
            employeeDTO.setDaysAvailable(availability);
        }

        return employeeDTO;
    }

    public PetDTO convertEntityToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getType());
        return petDTO;
    }

    public Pet convertPetDTOToEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(new Customer());
        pet.getCustomer().setId(petDTO.getOwnerId());
        pet.setType(petDTO.getType());
        return pet;
    }

    public ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if(schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        if(schedule.getPets() != null && schedule.getPets().size() > 0) {
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        scheduleDTO.setPetIds(petIds);

        if(schedule.getActivities() != null && schedule.getActivities().size() > 0) {
            Set<EmployeeSkill> activities = new HashSet<>(schedule.getActivities());
            scheduleDTO.setActivities(activities);
        }

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setEmployees(new ArrayList<>());
        schedule.setPets(new ArrayList<>());
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            scheduleDTO.getEmployeeIds().forEach(employeeId -> {
                Employee employee = new Employee();
                employee.setId(employeeId);
                schedule.getEmployees().add(employee);
            });
        }

        if(scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            scheduleDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                schedule.getPets().add(pet);
            });
        }


        return schedule;
    }

}
