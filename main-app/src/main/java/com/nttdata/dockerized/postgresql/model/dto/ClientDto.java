package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ClientDto {

    private Long id;
    private String name;
    private String email;
    private String status;
    private LocalDateTime registrationDate;
}
