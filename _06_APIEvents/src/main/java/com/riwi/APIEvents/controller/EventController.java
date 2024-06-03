package com.riwi.APIEvents.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.APIEvents.entities.Event;
import com.riwi.APIEvents.service.abstract_service.IEventService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController
{
    @Autowired
    private final IEventService IEventService;
    
    @GetMapping
    //Un generico que devuelve una lista de genericos tipo productos
    public ResponseEntity<Page<Event>> list(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "4") int size)
    {
        Page<Event> list = this.IEventService.findAllPaginate(page - 1, size);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public ResponseEntity<Event> add(@Validated @RequestBody Event event)
    { 
        if (this.dateValidate(event) && event.getCapacity() > 0) 
        {
            return ResponseEntity.ok(this.IEventService.save(event));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id)
    {
        Event event = this.IEventService.findById(id);
        
        if (event != null) 
        {
            this.IEventService.delete(event);
            return ResponseEntity.ok(true);
        }
        
        return ResponseEntity.ok(false);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable String id, @Validated @RequestBody EventRequest event)
    {
        if(this.dateValidate(event) && event.getCapacity() > 0) return ResponseEntity.ok(this.IEventService.update(id, event));

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable String id)
    {
        return ResponseEntity.ok(this.IEventService.findById(id));
    }

    public boolean dateValidate(Event event)
    {
        Date insertDate = event.getDate_event();
        Date actualDate = new Date(); 

        if (insertDate.before(actualDate)) return false;
 
        return true;
    }
}