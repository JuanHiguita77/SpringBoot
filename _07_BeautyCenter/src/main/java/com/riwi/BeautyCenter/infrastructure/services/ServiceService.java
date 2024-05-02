package com.riwi.BeautyCenter.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.riwi.BeautyCenter.api.dto.request.ServiceRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.api.dto.response.ServiceResponse;
import com.riwi.BeautyCenter.domain.entities.Appointment;
import com.riwi.BeautyCenter.domain.entities.Service;
import com.riwi.BeautyCenter.domain.repositories.ServiceRepository;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IServiceService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

//Se pone asi cuando chocan dos nombres iguales en este caso el Service
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService implements IServiceService
{
    private final ServiceRepository serviceRepository;
    
    @Override
    public void delete(Long id) 
    {
        this.serviceRepository.deleteById(id);
    }

    @Override
    public ServiceResponse insert(ServiceRequest request) 
    {
        Service service = this.RequestToService(request, new Service());

        return this.entityToResponse(this.serviceRepository.save(service));
    }
    
    @Override
    public Page<ServiceResponse> list(int page, int size) {
        if(page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);//Hace la peticion a la bd de cuantos datos traer

        return this.serviceRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public ServiceResponse update(Long id, ServiceRequest request) {
        
        Service service = this.serviceRepository.findById(id).orElse(null);

        if (service != null) 
        {
            Service newService = this.RequestToService(request, new Service());
            return this.entityToResponse(this.serviceRepository.save(newService));    
        }
        else
        {
            throw new EntityNotFoundException("Service with id " + id + " not found");
        }
    }
    
    public ServiceResponse entityToResponse(Service service)
    {
        ServiceResponse response = new ServiceResponse();
        
        BeanUtils.copyProperties(service, response);

        response.setAppointments(service.getAppointments().stream().map(this::AppointmentToResponse).collect(Collectors.toList()));

        return response;
    }

    public Service RequestToService(ServiceRequest request, Service service) 
    {
        BeanUtils.copyProperties(request, service);
        service.setAppointments(new ArrayList<>());
        
        return service;
    }

    private AppointmentResponse AppointmentToResponse(Appointment appointment)
    {
        AppointmentResponse response = new AppointmentResponse();

        BeanUtils.copyProperties(appointment, response);

        return response;
    }
    
    public ServiceResponse findById(Long id)
    {
        ServiceResponse service = this.entityToResponse(this.serviceRepository.findById(id).orElse(null));

        if (service != null) 
        {
            return service;
        }
        else
        {
           throw new EntityNotFoundException("Service with id " + id + " not found");
        }
    } 
}
