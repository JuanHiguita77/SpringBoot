package com.riwi.APIEvents.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.riwi.APIEvents.entities.Event;
import com.riwi.APIEvents.repositories.EventRepository;
import com.riwi.APIEvents.service.EventService;

@SpringBootTest

public class EventServiceTest {
    
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository);
    }

    @Test
    void getAllTest() {
        // Datos simulados
        Event event1 = new Event("1", "Event 1", LocalDate.now(), "Location 1", 100);
        Event event2 = new Event("2", "Event 2", LocalDate.now(), "Location 2", 200);

        // Comportamiento simulado
        List<Event> mockEvents = Arrays.asList(event1, event2);

        when(eventRepository.findAll()).thenReturn(mockEvents);

        // Ejecución del método
        List<Event> events = eventService.getAll();

        // Verificación de resultados
        assertEquals(2, events.size(), "El tamaño de la lista no es el esperado");
        assertTrue(events.contains(event1), "La lista no contiene el evento esperado");
        assertTrue(events.contains(event2), "La lista no contiene el evento esperado");

        // Verificar que el repositorio fue llamado exactamente una vez
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testFindAllPaginateValidInput() {
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        // Datos simulados
        List<Event> events = List.of(
                new Event("1", "Event 1", LocalDate.now(), "Location 1", 100),
                new Event("2", "Event 2", LocalDate.now(), "Location 2", 200)
        );

        //Repository return
        Page<Event> eventPage = new PageImpl<>(events, pageable, events.size());

        // Mock
        Mockito.when(eventRepository.findAll(pageable)).thenReturn(eventPage);

        // Act
        Page<Event> result = eventService.findAllPaginate(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Event 1", result.getContent().get(0).getName());

        // Verificar la interacción con el repositorio
        Mockito.verify(eventRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testFindAllPaginateNegativePage() {
        // Arrange
        int page = -1; // Página negativa
        int size = 5;
        Pageable pageable = PageRequest.of(0, size); // Debería ajustarse a 0

        // Datos simulados
        List<Event> events = List.of(
                new Event("1", "Event 1", LocalDate.now(), "Location 1", 100)
        );

        //Repository return
        Page<Event> eventPage = new PageImpl<>(events, pageable, events.size());

        // Mock
        Mockito.when(eventRepository.findAll(pageable)).thenReturn(eventPage);

        // Act
        Page<Event> result = eventService.findAllPaginate(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Event 1", result.getContent().get(0).getName());

        // Verificar la interacción con el repositorio
        Mockito.verify(eventRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testSave() {
        // Arrange
        Event event = new Event("1", "Test Event", LocalDate.now(), "Test Location", 150);

        // Mock del repositorio
        Mockito.when(eventRepository.save(event)).thenReturn(event);

        // Act
        Event result = eventService.save(event);

        // Assert
        assertNotNull(result); // Verifica que no sea null
        assertEquals(event.getId(), result.getId());
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getUbication(), result.getUbication());
        assertEquals(event.getCapacity(), result.getCapacity());

        // Verificar que el repositorio fue llamado
        Mockito.verify(eventRepository, Mockito.times(1)).save(event);
    }

    @Test
    void testDeleteValidEvent() {
        // Arrange
        Event event = new Event("1", "Sample Event", LocalDate.now(), "Sample Location", 100);

        // No es necesario configurar el mock para delete, ya que no devuelve un resultado.

        // Act
        eventService.delete(event);

        // Assert
        // Verifica que el método delete del repositorio fue llamado con el evento correcto.
        Mockito.verify(eventRepository, Mockito.times(1)).delete(event);
    }

    @Test
    void testFindByIdValidId() {
        // Arrange
        String id = "1";
        Event event = new Event(id, "Sample Event", LocalDate.now(), "Sample Location", 100);

        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        // Act
        Event result = eventService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Sample Event", result.getName());
        Mockito.verify(eventRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testFindByIdInvalidId() {
        // Arrange
        String id = "999";
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Event result = eventService.findById(id);

        // Assert
        assertNull(result);
        Mockito.verify(eventRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testUpdateValidId() {
        // Arrange
        String id = "1";
        Event existingEvent = new Event(id, "Old Event", LocalDate.now(), "Old Location", 100);
        Event updatedEvent = new Event(null, "Updated Event", LocalDate.now().plusDays(1), "Updated Location", 200);

        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));

        //Devolver el evento actualizado del repo
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Event result = eventService.update(id, updatedEvent);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Event", result.getName());
        assertEquals("Updated Location", result.getUbication());
        Mockito.verify(eventRepository, Mockito.times(1)).findById(id);
        Mockito.verify(eventRepository, Mockito.times(1)).save(updatedEvent);
    }

    @Test
    void testUpdateInvalidId() {
        // Arrange
        String id = "999";
        Event updatedEvent = new Event(null, "Updated Event", LocalDate.now().plusDays(1), "Updated Location", 200);

        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> eventService.update(id, updatedEvent));
        Mockito.verify(eventRepository, Mockito.times(1)).findById(id);
        //Verifica que el repositorio no se haya llamado
        Mockito.verify(eventRepository, Mockito.never()).save(Mockito.any(Event.class));
    }
}
