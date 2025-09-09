package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientSaveResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
}
