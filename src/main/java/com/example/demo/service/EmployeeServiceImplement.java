package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDTO;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplement implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImplement(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Employee not found with id: " + id));

        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employee.setEmail(employeeDTO.email());

        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail());
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employee.setEmail(employeeDTO.email());

        return employee;
    }
}