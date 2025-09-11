package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exception.BadRequestException;
import com.nttdata.dockerized.postgresql.exception.InternalServerErrorException;
import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.exception.ValidationException;
import com.nttdata.dockerized.postgresql.model.entity.Client;
import com.nttdata.dockerized.postgresql.repository.ClientRepository;
import com.nttdata.dockerized.postgresql.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de clientes
 * Implementa la lógica de negocio para operaciones CRUD
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(final Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(
                "El ID del cliente debe ser un número positivo",
                "Client",
                id
            );
        }
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public Client save(final Client client) {
        try {
            // Validaciones de negocio
            validateClientForSave(client);
            
            // Verificar si ya existe un cliente con el mismo email
            if (clientRepository.findAll().stream()
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(client.getEmail()))) {
                throw new BadRequestException(
                    "Ya existe un cliente con el email: " + client.getEmail(),
                    "Client",
                    null,
                    "El email del cliente debe ser único"
                );
            }
            
            // Establecer valores por defecto si no están definidos
            client.setActive(Optional.ofNullable(client.getActive()).orElse(Boolean.TRUE));
            client.setRegistrationDate(Optional.ofNullable(client.getRegistrationDate()).orElse(LocalDateTime.now()));
            
            return clientRepository.save(client);
        } catch (Exception e) {
            log.error("Error al guardar cliente: {}", e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al guardar el cliente",
                "Client",
                e
            );
        }
    }

    @Override
    @Transactional
    public Optional<Client> update(final Long id, final Client client) {
        try {
            // Validar ID
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID del cliente debe ser un número positivo",
                    "Client",
                    id
                );
            }
            
            // Verificar si existe el cliente
            if (!clientRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el cliente con ID: " + id,
                    "Client",
                    id
                );
            }
            
            // Validaciones de negocio
            validateClientForUpdate(client);
            
            // Verificar si ya existe otro cliente con el mismo email
            if (clientRepository.findAll().stream()
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(client.getEmail()) && !c.getId().equals(id))) {
                throw new BadRequestException(
                    "Ya existe otro cliente con el email: " + client.getEmail(),
                    "Client",
                    id,
                    "El email del cliente debe ser único"
                );
            }
            
            return Optional.of(clientRepository.save(this.setClientIdAndReturn(client, id)));
        } catch (Exception e) {
            log.error("Error al actualizar cliente con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al actualizar el cliente",
                "Client",
                id,
                e
            );
        }
    }

    @Override
    @Transactional
    public boolean deleteById(final Long id) {
        try {
            // Validar ID
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID del cliente debe ser un número positivo",
                    "Client",
                    id
                );
            }
            
            // Verificar si existe el cliente
            if (!clientRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el cliente con ID: " + id,
                    "Client",
                    id
                );
            }
            
            return this.performDelete(id);
        } catch (Exception e) {
            log.error("Error al eliminar cliente con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al eliminar el cliente",
                "Client",
                id,
                e
            );
        }
    }

    @Override
    public boolean existsById(final Long id) {
        return clientRepository.existsById(id);
    }

    // Métodos auxiliares para operaciones internas
    private Client setClientIdAndReturn(final Client client, final Long id) {
        client.setId(id);
        return client;
    }

    private boolean performDelete(final Long id) {
        clientRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida un cliente antes de guardarlo
     */
    private void validateClientForSave(Client client) {
        if (client == null) {
            throw new ValidationException(
                "El cliente no puede ser nulo",
                "Client",
                List.of("El cliente es requerido")
            );
        }
        
        ValidationUtils.validateFields("Client",
            () -> ValidationUtils.validateNotNullOrEmpty(client.getName(), "name", "Client"),
            () -> ValidationUtils.validateStringLength(client.getName(), "name", 2, 100, "Client"),
            () -> ValidationUtils.validateNotNullOrEmpty(client.getEmail(), "email", "Client"),
            () -> ValidationUtils.validateEmail(client.getEmail(), "Client")
        );
    }
    
    /**
     * Valida un cliente antes de actualizarlo
     */
    private void validateClientForUpdate(Client client) {
        if (client == null) {
            throw new ValidationException(
                "El cliente no puede ser nulo",
                "Client",
                List.of("El cliente es requerido")
            );
        }
        
        ValidationUtils.validateFields("Client",
            () -> ValidationUtils.validateNotNullOrEmpty(client.getName(), "name", "Client"),
            () -> ValidationUtils.validateStringLength(client.getName(), "name", 2, 100, "Client"),
            () -> ValidationUtils.validateNotNullOrEmpty(client.getEmail(), "email", "Client"),
            () -> ValidationUtils.validateEmail(client.getEmail(), "Client")
        );
    }
}
