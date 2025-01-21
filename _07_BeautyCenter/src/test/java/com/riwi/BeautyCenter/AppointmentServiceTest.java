package com.riwi.BeautyCenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import com.riwi.BeautyCenter.api.dto.request.AppointmentRequest;
import com.riwi.BeautyCenter.api.dto.response.AppointmentResponse;
import com.riwi.BeautyCenter.domain.entities.Appointment;
import com.riwi.BeautyCenter.domain.entities.Client;
import com.riwi.BeautyCenter.domain.entities.Employee;
import com.riwi.BeautyCenter.domain.entities.Service;
import com.riwi.BeautyCenter.domain.repositories.AppointmentRepository;
import com.riwi.BeautyCenter.domain.repositories.ClientRepository;
import com.riwi.BeautyCenter.domain.repositories.EmployeeRepository;
import com.riwi.BeautyCenter.domain.repositories.ServiceRepository;
import com.riwi.BeautyCenter.infrastructure.services.AppointmentService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @Mock
    private ClientRepository clientRepository;
    
    @Mock
    private EmployeeRepository employeeRepository;
    
    @Mock
    private ServiceRepository serviceRepository;
    
    @InjectMocks
    private AppointmentService appointmentService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(appointmentRepository, clientRepository, employeeRepository, serviceRepository);
    }
    
    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;

        // Crear datos simulados
        Client client = new Client();
        client.setClient_id(1L);
        client.setFirst_name("John");
        client.setLast_name("Doe");
        client.setPhone("1234567890");
        client.setEmail("john.doe@example.com");

        Employee employee = new Employee();
        employee.setEmployee_id(1L);
        employee.setFirst_name("Jane");
        employee.setLast_name("Smith");
        employee.setPhone("0987654321");
        employee.setEmail("jane.smith@example.com");
        employee.setRole("Manager");

        Service service = new Service();
        service.setService_id(1L);
        service.setName("Haircut");
        service.setDescription("Basic haircut service");
        service.setPrice(BigDecimal.valueOf(15.00));

        Appointment appointment = new Appointment();
        appointment.setAppointment_id(id);
        appointment.setDate_time(Timestamp.valueOf("2025-01-16 10:00:00"));
        appointment.setDuration(30);
        appointment.setComments("First-time appointment");
        appointment.setClient(client);
        appointment.setEmployee(employee);
        appointment.setService(service);

        // Mock del repositorio
        Mockito.when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));

        // Act
        AppointmentResponse result = appointmentService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getAppointment_id());
        assertEquals(appointment.getDate_time(), result.getDate_time());
        assertEquals(appointment.getDuration(), result.getDuration());
        assertEquals(appointment.getComments(), result.getComments());

        // Verifica datos del cliente
        assertNotNull(result.getClientResponse());
        assertEquals(client.getClient_id(), result.getClientResponse().getClient_id());
        assertEquals(client.getFirst_name(), result.getClientResponse().getFirst_name());
        assertEquals(client.getLast_name(), result.getClientResponse().getLast_name());

        // Verifica datos del empleado
        assertNotNull(result.getEmployeeResponse());
        assertEquals(employee.getEmployee_id(), result.getEmployeeResponse().getEmployee_id());
        assertEquals(employee.getFirst_name(), result.getEmployeeResponse().getFirst_name());
        assertEquals(employee.getLast_name(), result.getEmployeeResponse().getLast_name());

        // Verifica datos del servicio
        assertNotNull(result.getServiceResponse());
        assertEquals(service.getService_id(), result.getServiceResponse().getService_id());
        assertEquals(service.getName(), result.getServiceResponse().getName());

        // Verificar que el repositorio fue llamado
        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testFindById_InvalidId() {
        // Arrange
        Long id = 999L;

        // Mockear el repositorio para devolver vacío
        Mockito.when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

       
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.findById(id);
        });

        assertEquals("Appointment not found with id: 999", exception.getMessage());
        
        // Verificar la interacción con el repositorio
        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        appointmentService.delete(id);

        // Assert
        Mockito.verify(appointmentRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testInsert() {
        // Arrange
        AppointmentRequest request = new AppointmentRequest();
        request.setDate_time(Timestamp.valueOf("2025-01-16 10:00:00"));
        request.setDuration(30);
        request.setComments("First-time appointment");
        request.setClient_id(1L);
        request.setEmployee_id(2L);
        request.setService_id(3L);

        // Simula las entidades relacionadas
        Client client = new Client();
        client.setClient_id(1L);

        Employee employee = new Employee();
        employee.setEmployee_id(2L);

        Service service = new Service();
        service.setService_id(3L);

        // Mockear los repositorios para devolver las entidades relacionadas
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));
        Mockito.when(serviceRepository.findById(3L)).thenReturn(Optional.of(service));

        // Simula el objeto Appointment que se guardará
        Appointment appointment = new Appointment();
        appointment.setAppointment_id(1L);
        appointment.setDate_time(request.getDate_time());
        appointment.setDuration(request.getDuration());
        appointment.setComments(request.getComments());
        appointment.setClient(client);
        appointment.setEmployee(employee);
        appointment.setService(service);

        Mockito.when(appointmentRepository.save(Mockito.any(Appointment.class))).thenReturn(appointment);

        // Act
        AppointmentResponse result = appointmentService.insert(request);

        // Assert
        assertNotNull(result);
        assertEquals(appointment.getAppointment_id(), result.getAppointment_id());
        assertEquals(request.getDate_time(), result.getDate_time());
        assertEquals(request.getDuration(), result.getDuration());
        assertEquals(request.getComments(), result.getComments());

        // Verifica los datos relacionados en la respuesta
        assertNotNull(result.getClientResponse());
        assertEquals(client.getClient_id(), result.getClientResponse().getClient_id());

        assertNotNull(result.getEmployeeResponse());
        assertEquals(employee.getEmployee_id(), result.getEmployeeResponse().getEmployee_id());

        assertNotNull(result.getServiceResponse());
        assertEquals(service.getService_id(), result.getServiceResponse().getService_id());

        // Verifica que los repositorios sean llamados
        Mockito.verify(clientRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(3L);
        Mockito.verify(appointmentRepository, Mockito.times(1)).save(Mockito.any(Appointment.class));
    }

    @Test
    void testList() {
        // Arrange
        int page = 0;
        int size = 2;

        Appointment appointment1 = new Appointment();
        appointment1.setAppointment_id(1L);
        appointment1.setDate_time(Timestamp.valueOf("2025-01-16 10:00:00"));
        appointment1.setDuration(30);
        appointment1.setComments("First appointment");

        Appointment appointment2 = new Appointment();
        appointment2.setAppointment_id(2L);
        appointment2.setDate_time(Timestamp.valueOf("2025-01-17 11:00:00"));
        appointment2.setDuration(45);
        appointment2.setComments("Second appointment");

        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);
        Page<Appointment> mockPage = new PageImpl<>(appointments);

        Mockito.when(appointmentRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(mockPage);

        // Act
        Page<AppointmentResponse> result = appointmentService.list(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getAppointment_id());
        assertEquals("First appointment", result.getContent().get(0).getComments());
        assertEquals(2L, result.getContent().get(1).getAppointment_id());
        assertEquals("Second appointment", result.getContent().get(1).getComments());

        // Verify repository interaction
        Mockito.verify(appointmentRepository, Mockito.times(1))
            .findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void testList_NegativePage() {
        // Arrange
        int page = -1;
        int size = 2;

        Appointment appointment = new Appointment();
        appointment.setAppointment_id(1L);
        appointment.setDate_time(Timestamp.valueOf("2025-01-16 10:00:00"));
        appointment.setDuration(30);
        appointment.setComments("Appointment with negative page");

        List<Appointment> appointments = Collections.singletonList(appointment);
        Page<Appointment> mockPage = new PageImpl<>(appointments);

        Mockito.when(appointmentRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(mockPage);

        // Act
        Page<AppointmentResponse> result = appointmentService.list(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getAppointment_id());

        // Verify repository interaction
        Mockito.verify(appointmentRepository, Mockito.times(1))
            .findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        Long id = 1L;
        AppointmentRequest request = new AppointmentRequest();
        request.setDate_time(Timestamp.valueOf("2025-01-17 14:00:00"));
        request.setDuration(60);
        request.setComments("Updated appointment");
        request.setClient_id(1L);
        request.setEmployee_id(2L);
        request.setService_id(3L);

        // Mock existing appointment
        Appointment existingAppointment = new Appointment();
        existingAppointment.setAppointment_id(id);
        existingAppointment.setDate_time(Timestamp.valueOf("2025-01-16 10:00:00"));
        existingAppointment.setDuration(30);
        existingAppointment.setComments("Old appointment");

        Client client = new Client();
        client.setClient_id(1L);

        Employee employee = new Employee();
        employee.setEmployee_id(2L);

        Service service = new Service();
        service.setService_id(3L);

        // Mock repositories
        Mockito.when(appointmentRepository.findById(id)).thenReturn(Optional.of(existingAppointment));
        Mockito.when(clientRepository.findById(request.getClient_id())).thenReturn(Optional.of(client));
        Mockito.when(employeeRepository.findById(request.getEmployee_id())).thenReturn(Optional.of(employee));
        Mockito.when(serviceRepository.findById(request.getService_id())).thenReturn(Optional.of(service));
        Mockito.when(appointmentRepository.save(Mockito.any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AppointmentResponse result = appointmentService.update(id, request);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getAppointment_id());
        assertEquals(request.getDate_time(), result.getDate_time());
        assertEquals(request.getDuration(), result.getDuration());
        assertEquals(request.getComments(), result.getComments());

        // Verify interactions
        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(id);
        Mockito.verify(clientRepository, Mockito.times(1)).findById(request.getClient_id());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(request.getEmployee_id());
        Mockito.verify(serviceRepository, Mockito.times(1)).findById(request.getService_id());
        Mockito.verify(appointmentRepository, Mockito.times(1)).save(Mockito.any(Appointment.class));
    }


    @Test
    void testUpdate_AppointmentNotFound() {
        // Arrange
        Long id = 1L;
        AppointmentRequest request = new AppointmentRequest();
        request.setDate_time(Timestamp.valueOf("2025-01-17 14:00:00"));
        request.setDuration(60);
        request.setComments("Attempted update");

        Mockito.when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.update(id, request);
        });

        assertEquals("Employee with id " + id + " not found", exception.getMessage());

        // Verify no further interactions
        Mockito.verify(appointmentRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(appointmentRepository);
        Mockito.verifyNoInteractions(clientRepository, employeeRepository, serviceRepository);
    }

}
