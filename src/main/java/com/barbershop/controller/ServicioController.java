package com.barbershop.controller;

import com.barbershop.entity.Servicio;
import com.barbershop.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<Servicio> listar() {
        return servicioService.listarServiciosActivos();
    }

    @GetMapping("/{id}")
    public Servicio buscar(@PathVariable Long id) {
        return servicioService.buscarPorId(id);
    }

    @PostMapping
    public Servicio guardar(@Valid @RequestBody Servicio servicio) {
        return servicioService.guardar(servicio);
    }

    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id,
                               @Valid @RequestBody Servicio servicio) {
        return servicioService.actualizar(id, servicio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}