package com.riwi.BeautyCenter.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.BeautyCenter.api.dto.request.EmployeeRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.api.dto.response.EmployeeResponse;
import com.riwi.BeautyCenter.domain.entities.Appointment;
import com.riwi.BeautyCenter.domain.entities.Employee;
import com.riwi.BeautyCenter.domain.repositories.EmployeeRepository;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IEmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService 
{
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse findById(Long id) 
    {
        return this.entityToResponse(this.employeeRepository.findById(id).orElse(null)); 
    }

    @Override
    public void delete(Long id) 
    {
        this.employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse insert(EmployeeRequest request) 
    {
        Employee employee = this.RequestToEmployee(request, new Employee());

        return this.entityToResponse(this.employeeRepository.save(employee));
    }

    @Override
    public Page<EmployeeResponse> list(int page, int size) 
    {
        if(page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);//Hace la peticion a la bd de cuantos datos traer
        
        return this.employeeRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) 
    {
        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (employee != null) 
        {
            Employee newEmployee = this.RequestToEmployee(request, employee);

            return this.entityToResponse(this.employeeRepository.save(newEmployee));     
        }
        else
        {
            throw new EntityNotFoundException("Employee with id " + id + " not found");
        }        
    }
    
    public EmployeeResponse entityToResponse(Employee employee)
    {
        EmployeeResponse response = new EmployeeResponse();
        
        BeanUtils.copyProperties(employee, response);

        response.setAppointments(employee.getAppointments().stream().map(this::AppointmentToResponse).collect(Collectors.toList()));

        return response;
    }

    public Employee RequestToEmployee(EmployeeRequest request, Employee employee) 
    {
        BeanUtils.copyProperties(request, employee);
        employee.setAppointments(new ArrayList<>());

        return employee;
    }

    private AppointmentResponse AppointmentToResponse(Appointment appointment)
    {
        AppointmentResponse response = new AppointmentResponse();

        BeanUtils.copyProperties(appointment, response);

        return response;
    }
}
