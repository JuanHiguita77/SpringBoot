package com.riwi.BeautyCenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.riwi.BeautyCenter.api.dto.request.EmployeeRequest;
import com.riwi.BeautyCenter.api.dto.response.EmployeeResponse;
import com.riwi.BeautyCenter.domain.entities.Employee;
import com.riwi.BeautyCenter.domain.repositories.EmployeeRepository;
import com.riwi.BeautyCenter.infrastructure.services.EmployeeService;
import com.riwi.BeautyCenter.util.exceptions.IdNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testFindById_Success() {
        // Arrange
        Long id = 1L;
        Employee employee = new Employee();
        employee.setEmployee_id(id);
        employee.setFirst_name("John");
        employee.setLast_name("Doe");
        employee.setAppointments(new ArrayList<>()); // Evitar NullPointerException

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        EmployeeResponse response = employeeService.findById(id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getEmployee_id());
        assertEquals("John", response.getFirst_name());
        assertEquals("Doe", response.getLast_name());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long id = 1L;
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
         // Act & Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            employeeService.findById(id);
        });

        assertEquals("Dont found Entity Employee With id typed", exception.getMessage());

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testDelete_Success() {
        // Arrange
        Long id = 1L;

        // Act
        employeeService.delete(id);

        // Assert
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testInsert_Success() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setFirst_name("Jane");
        request.setLast_name("Doe");
        request.setEmail("jane.doe@example.com");
        request.setPhone("123456789");
        request.setRole("Manager");

        Employee employee = new Employee();
        employee.setEmployee_id(1L);
        employee.setFirst_name("Jane");
        employee.setLast_name("Doe");
        employee.setEmail("jane.doe@example.com");
        employee.setPhone("123456789");
        employee.setRole("Manager");
        employee.setAppointments(new ArrayList<>()); // Evitar NullPointerException

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        // Act
        EmployeeResponse response = employeeService.insert(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getEmployee_id());
        assertEquals("Jane", response.getFirst_name());
        assertEquals("Doe", response.getLast_name());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(Mockito.any(Employee.class));
    }

    @Test
    void testList_Success() {
        // Arrange
        int page = 0;
        int size = 5;

        Employee employee = new Employee();
        employee.setEmployee_id(1L);
        employee.setFirst_name("John");
        employee.setLast_name("Doe");
        employee.setAppointments(new ArrayList<>()); // Evitar NullPointerException

        Page<Employee> employeePage = new PageImpl<>(List.of(employee));
        Mockito.when(employeeRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(employeePage);

        // Act
        Page<EmployeeResponse> response = employeeService.list(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("John", response.getContent().get(0).getFirst_name());
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        Long id = 1L;
        EmployeeRequest request = new EmployeeRequest();
        request.setFirst_name("Updated");
        request.setLast_name("Doe");
        request.setEmail("updated.doe@example.com");
        request.setPhone("987654321");
        request.setRole("Supervisor");

        Employee existingEmployee = new Employee();
        existingEmployee.setEmployee_id(id);
        existingEmployee.setFirst_name("John");
        existingEmployee.setLast_name("Doe");
        existingEmployee.setEmail("john.doe@example.com");
        existingEmployee.setPhone("123456789");
        existingEmployee.setRole("Manager");
        existingEmployee.setAppointments(new ArrayList<>()); // Evitar NullPointerException

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(existingEmployee);

        // Act
        EmployeeResponse response = employeeService.update(id, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated", response.getFirst_name());
        assertEquals("Supervisor", response.getRole());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(Mockito.any(Employee.class));
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        Long id = 1L;
        EmployeeRequest request = new EmployeeRequest();
        request.setFirst_name("Updated");
        request.setLast_name("Doe");
        request.setEmail("updated.doe@example.com");
        request.setPhone("987654321");
        request.setRole("Supervisor");

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeService.update(id, request);
        });

        assertEquals("Employee with id " + id + " not found", exception.getMessage());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
    }
}
