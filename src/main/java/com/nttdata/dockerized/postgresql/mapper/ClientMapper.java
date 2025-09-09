package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.ClientDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientSaveResponseDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientUpdateRequestDto;
import com.nttdata.dockerized.postgresql.model.entity.Client;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto map(Client client);

    List<ClientDto> map(List<Client> clients);

    Client toEntity(ClientSaveRequestDto clientSaveRequestDto);

    Client toEntity(ClientUpdateRequestDto clientUpdateRequestDto);

    ClientSaveResponseDto toClientSaveResponseDto(Client client);

    @AfterMapping
    default void setRemainingValues(final Client client, @MappingTarget final ClientDto clientDto) {
        clientDto.setStatus(
                Optional.ofNullable(client.getActive())
                        .map(active -> active ? "Active" : "Inactive")
                        .orElse("Inactive")
        );
    }
}
