package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSaveRequestDto {

    private String name;
    private String email;
}
