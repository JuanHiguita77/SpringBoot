package com.riwi.APIEvents.service.abstract_service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.riwi.APIEvents.entities.Event;

//INTERFACE QUE SERÁ LA UNICA QUE SE MUESTRE DE CARA AL PUBLICO ANTES DE LA LOGICA EN SI
//SEGURIDAD PARA EVITAR VER EL CODIGO INTERNO
public interface IEventService 
{
    public Event save(Event event); 

    public List<Event> getAll();

    public Event findById(String id);

    public void delete(Event event);

    public Event update(String id, Event event); 

    public Page<Event> findAllPaginate(int page, int size);
}
