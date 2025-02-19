package com.riwi.BeautyCenter.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.BeautyCenter.api.dto.request.AppointmentRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.api.dto.response.ClientResponse;
import com.riwi.BeautyCenter.api.dto.response.EmployeeResponse;
import com.riwi.BeautyCenter.api.dto.response.ServiceResponse;
import com.riwi.BeautyCenter.domain.entities.Appointment;
import com.riwi.BeautyCenter.domain.entities.Client;
import com.riwi.BeautyCenter.domain.entities.Employee;
import com.riwi.BeautyCenter.domain.entities.Service;
import com.riwi.BeautyCenter.domain.repositories.AppointmentRepository;
import com.riwi.BeautyCenter.domain.repositories.ClientRepository;
import com.riwi.BeautyCenter.domain.repositories.EmployeeRepository;
import com.riwi.BeautyCenter.domain.repositories.ServiceRepository;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IAppointmentService;
import com.riwi.BeautyCenter.util.exceptions.IdNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class AppointmentService implements IAppointmentService{

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final ClientRepository clientRepository;
    
    @Autowired
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    private final ServiceRepository serviceRepository;
    
    @Override
    public AppointmentResponse findById(Long id) 
    {
        return this.entityToResponse(this.appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id)));
    }

    @Override
    public void delete(Long id) 
    {
        this.appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentResponse insert(AppointmentRequest request) 
    {
        Appointment appointment = this.RequestToAppointment(request, new Appointment());
        return this.entityToResponse(this.appointmentRepository.save(appointment));
    }

    @Override
    public Page<AppointmentResponse> list(int page, int size) 
    {
        if(page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);
        return this.appointmentRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        
        Appointment appointmentResponse = this.appointmentRepository.findById(id).orElse(null);

        if(appointmentResponse != null)
        {
            Appointment appointment = this.RequestToAppointment(request, appointmentResponse);
            return this.entityToResponse(this.appointmentRepository.save(appointment));
        }
        else
        {
            throw new EntityNotFoundException("Employee with id " + id + " not found");
        }
    }
    
    public AppointmentResponse entityToResponse(Appointment appointment)
    {
        AppointmentResponse response = new AppointmentResponse();

        BeanUtils.copyProperties(appointment, response);

        ClientResponse clientResponse = this.entityToResponseGeneric(appointment.getClient(), ClientResponse.class);

        EmployeeResponse employeeResponse = this.entityToResponseGeneric(appointment.getEmployee(), EmployeeResponse.class);

        ServiceResponse serviceResponse = this.entityToResponseGeneric(appointment.getService(), ServiceResponse.class);

        response.setClientResponse(clientResponse);
        response.setEmployeeResponse(employeeResponse);
        response.setServiceResponse(serviceResponse);

        return response;
    }

    public Appointment RequestToAppointment(AppointmentRequest request, Appointment appointment) 
    {
        
        BeanUtils.copyProperties(request, appointment);
        
        Client client = this.find(request.getClient_id(), clientRepository, "client");
                                            
        appointment.setClient(client);
        
        Employee employee = this.find(request.getEmployee_id(), employeeRepository, "employee");
                                                   
        appointment.setEmployee(employee);
        
        Service service = this.find(request.getService_id(), serviceRepository, "service");
                                                 
        appointment.setService(service);
        
        return appointment;
    }

    public <T> T find(Long id, JpaRepository<T, Long> repository, String entity) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(entity));
    }

    public <T, R> R entityToResponseGeneric(T entity, Class<R> responseType) {
        try {
            R response = responseType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
