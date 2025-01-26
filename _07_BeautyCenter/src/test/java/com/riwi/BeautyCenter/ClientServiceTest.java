package com.riwi.BeautyCenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.riwi.BeautyCenter.api.dto.request.ClientRequest;
import com.riwi.BeautyCenter.api.dto.response.ClientResponse;
import com.riwi.BeautyCenter.domain.entities.Client;
import com.riwi.BeautyCenter.domain.repositories.ClientRepository;
import com.riwi.BeautyCenter.infrastructure.services.ClientService;
import com.riwi.BeautyCenter.util.exceptions.IdNotFoundException;


public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository; 

    @InjectMocks
    private ClientService clientService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        Long id = 1L;
        Client client = new Client();
        client.setClient_id(id);
        client.setFirst_name("John");
        client.setLast_name("Doe");
        client.setAppointments(new ArrayList<>());
        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // Act
        ClientResponse response = clientService.findById(id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getClient_id());
        assertEquals("John", response.getFirst_name());
        assertEquals("Doe", response.getLast_name());
        Mockito.verify(clientRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long clientId = 1L;
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            clientService.findById(clientId);
        });

        assertEquals("Dont found Entity Client With id typed", exception.getMessage());
        Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
    }

    @Test
    void testDelete_Success() {
        // Arrange
        Long id = 1L;

        // Act
        clientService.delete(id);

        // Assert
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void testInsert_Success() {
        // Arrange
        ClientRequest request = new ClientRequest();
        request.setFirst_name("Jane");
        request.setLast_name("Doe");
        request.setPhone("123456789");
        request.setEmail("jane.doe@example.com");

        Client client = new Client();
        client.setClient_id(1L);
        client.setFirst_name("Jane");
        client.setLast_name("Doe");
        client.setPhone("123456789");
        client.setEmail("jane.doe@example.com");
        client.setAppointments(new ArrayList<>());

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        // Act
        ClientResponse response = clientService.insert(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getClient_id());
        assertEquals("Jane", response.getFirst_name());
        assertEquals("Doe", response.getLast_name());
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
    }

    @Test
    void testList_Success() {
        // Arrange
        int page = 0;
        int size = 5;

        Client client = new Client();
        client.setClient_id(1L);
        client.setFirst_name("John");
        client.setLast_name("Doe");
        client.setAppointments(new ArrayList<>());

        Page<Client> clientPage = new PageImpl<>(List.of(client));
        Mockito.when(clientRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(clientPage);

        // Act
        Page<ClientResponse> response = clientService.list(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("John", response.getContent().get(0).getFirst_name());
        Mockito.verify(clientRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        Long id = 1L;

        ClientRequest request = new ClientRequest();
        request.setFirst_name("Updated");
        request.setLast_name("Client");
        request.setPhone("987654321");
        request.setEmail("updated.client@example.com");

        Client existingClient = new Client();
        existingClient.setClient_id(id);
        existingClient.setFirst_name("John");
        existingClient.setLast_name("Doe");

        Client updatedClient = new Client();
        updatedClient.setClient_id(id);
        updatedClient.setFirst_name("Updated");
        updatedClient.setLast_name("Client");
        updatedClient.setPhone("987654321");
        updatedClient.setEmail("updated.client@example.com");
        updatedClient.setAppointments(new ArrayList<>());

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(existingClient));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(updatedClient);

        // Act
        ClientResponse response = clientService.update(id, request);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getClient_id());
        assertEquals("Updated", response.getFirst_name());
        assertEquals("Client", response.getLast_name());
        assertEquals("987654321", response.getPhone());
        assertEquals("updated.client@example.com", response.getEmail());
        Mockito.verify(clientRepository, Mockito.times(1)).findById(id);
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
    }

    @Test
    void testUpdate_ClientNotFound() {
        // Arrange
        Long id = 1L;
        ClientRequest request = new ClientRequest();
        request.setFirst_name("Updated");
        request.setLast_name("Client");

        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            clientService.update(id, request);
        });

        assertEquals("Dont found Entity Client With id typed", exception.getMessage());
        Mockito.verify(clientRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(clientRepository);
    }
}
