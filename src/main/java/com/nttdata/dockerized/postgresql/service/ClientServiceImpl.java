package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Client;
import com.nttdata.dockerized.postgresql.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de clientes
 * Implementa la lógica de negocio para operaciones CRUD
 */
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
        return clientRepository.findById(id);
    }

    @Override
    public Client save(final Client client) {
        // Establecer valores por defecto si no están definidos
        client.setActive(Optional.ofNullable(client.getActive()).orElse(Boolean.TRUE));
        client.setRegistrationDate(Optional.ofNullable(client.getRegistrationDate()).orElse(LocalDateTime.now()));
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> update(final Long id, final Client client) {
        // Actualizar solo si el cliente existe
        return clientRepository.existsById(id) 
            ? Optional.of(clientRepository.save(this.setClientIdAndReturn(client, id)))
            : Optional.empty();
    }

    @Override
    public boolean deleteById(final Long id) {
        // Eliminar solo si existe
        return clientRepository.existsById(id) ? this.performDelete(id) : false;
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
}
