package com.riwi.APIEvents.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.APIEvents.entities.Event;
import com.riwi.APIEvents.repositories.EventRepository;
import com.riwi.APIEvents.service.abstract_service.IEventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //Constructor para las dependencias
public class EventService implements IEventService
{
    @Autowired
    private final EventRepository eventRepository;

    @Override
    public List<Event> getAll() 
    {
        return this.eventRepository.findAll();
    }

    public Page<Event> findAllPaginate(int page, int size)
    {
        //Validar que no sea menor a cero
        if (page < 0) 
        {
            page = 0;    
        }

        //Crear paginacion
        Pageable pageable = PageRequest.of(page, size);
        return this.eventRepository.findAll(pageable);
    }

    @Override
    public Event save(Event event)
    {
        return this.eventRepository.save(event);
    }

    @Override
    public void delete(Event event) 
    { 
        this.eventRepository.delete(event);
    }

    @Override
    public Event findById(String id) 
    {
        return this.eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event update(String id, Event event) 
    {
        this.eventRepository.findById(id).orElseThrow();
        
        event.setId(id);
        return this.eventRepository.save(event);
    }
}
