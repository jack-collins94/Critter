package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.ConvertDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    ConvertDTO convertDTO;

    public EmployeeDTO save(EmployeeDTO employeeDTO){
        Employee employee = convertDTO.convertEmployeeDTOToEntity(employeeDTO);
        return convertDTO.convertEntityToEmployeeDTO(repository.save(employee));
    }

    public EmployeeDTO getEmployeeById(Long id){
        Employee employee = repository.getOne(id);
        return convertDTO.convertEntityToEmployeeDTO(employee);
    }

    public void setAvailability(Set<DayOfWeek> dayOfWeeks, Long id){
        Employee employee = repository.getOne(id);
        employee.setDaysAvailable(dayOfWeeks);
    }

    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeDTO){
        List<Employee> employees = repository.findDistinctByDaysAvailableAndSkillsIn(
                employeeDTO.getDate().getDayOfWeek(),
                employeeDTO.getSkills());

        List<EmployeeDTO> availableEmployees = new ArrayList<>();

        for(Employee employee:employees){
                availableEmployees.add(convertDTO.convertEntityToEmployeeDTO(employee));

        }
        return availableEmployees;
    }
}
