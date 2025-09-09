package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> listAll();

    Optional<Client> findById(Long id);

    Client save(Client client);

    Optional<Client> update(Long id, Client client);

    boolean deleteById(Long id);

    boolean existsById(Long id);
}
