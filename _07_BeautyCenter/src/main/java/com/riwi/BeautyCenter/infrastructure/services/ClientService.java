package com.riwi.BeautyCenter.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.BeautyCenter.api.dto.request.ClientRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.api.dto.response.ClientResponse;
import com.riwi.BeautyCenter.domain.entities.Appointment;
import com.riwi.BeautyCenter.domain.entities.Client;
import com.riwi.BeautyCenter.domain.repositories.ClientRepository;
import com.riwi.BeautyCenter.infrastructure.abstract_services.IClientService;
import com.riwi.BeautyCenter.util.exceptions.IdNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService implements IClientService{

    @Autowired
    private final ClientRepository clientRepository;

    @Override
    public ClientResponse findById(Long id) {
        return this.entityToResponse(this.clientRepository.findById(id).orElseThrow(() -> new IdNotFoundException(Client.class.getSimpleName())));
    }

    @Override
    public void delete(Long id) 
    {
        this.clientRepository.deleteById(id);
    }

    @Override
    public ClientResponse insert(ClientRequest request) 
    {
        Client client = this.RequestToClient(request, new Client());    
        return this.entityToResponse(this.clientRepository.save(client));
    }

    @Override
    public Page<ClientResponse> list(int page, int size) 
    {
        if (page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);
        return this.clientRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) 
    {
        Client clientResponse = this.clientRepository.findById(id).orElse(null);

        if (clientResponse != null) 
        {
            Client client = this.RequestToClient(request, clientResponse);
            return this.entityToResponse(this.clientRepository.save(client));
        }
        else
        {
            throw new IdNotFoundException(Client.class.getSimpleName());
        }
    }
    
    public ClientResponse entityToResponse(Client client)
    {
        ClientResponse response = new ClientResponse();
        
        BeanUtils.copyProperties(client, response);

        response.setAppointments(client.getAppointments().stream().map(this::AppointmentToResponse).collect(Collectors.toList()));

        return response;
    }

    public Client RequestToClient(ClientRequest request, Client client) 
    {
        BeanUtils.copyProperties(request, client);
        client.setAppointments(new ArrayList<>());

        return client;
    }

    private AppointmentResponse AppointmentToResponse(Appointment appointment)
    {
        AppointmentResponse response = new AppointmentResponse();

        BeanUtils.copyProperties(appointment, response);

        return response;
    }
}
