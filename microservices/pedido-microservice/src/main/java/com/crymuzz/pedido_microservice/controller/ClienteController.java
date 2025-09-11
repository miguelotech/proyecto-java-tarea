package com.crymuzz.pedido_microservice.controller;

import com.crymuzz.pedido_microservice.model.dto.CreateClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.UpdateClienteDTO;
import com.crymuzz.pedido_microservice.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseClienteDTO crear(@RequestBody @Valid CreateClienteDTO dto) {
        return clienteService.crearCliente(dto);
    }

    @GetMapping
    public List<ResponseClienteDTO> listar() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseClienteDTO obtener(@PathVariable Long id) {
        return clienteService.obtenerClienteId(id);
    }

    @PutMapping("/{id}")
    public ResponseClienteDTO actualizar(@PathVariable Long id, @RequestBody @Valid UpdateClienteDTO dto) {
        return clienteService.actualizarCliente(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }
}
