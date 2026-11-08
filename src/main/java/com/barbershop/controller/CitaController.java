package com.barbershop.controller;

import com.barbershop.entity.Cita;
import com.barbershop.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listar() {
        return citaService.listarCitas();
    }

    @GetMapping("/fecha/{fecha}")
    public List<Cita> listarPorFecha(@PathVariable LocalDate fecha) {
        return citaService.listarPorFecha(fecha);
    }

    @GetMapping("/{id}")
    public Cita buscar(@PathVariable Long id) {
        return citaService.buscarPorId(id);
    }

    @PostMapping
    public Cita guardar(@RequestBody Cita cita) {
        return citaService.guardar(cita);
    }

    @PatchMapping("/{id}/estado")
    public Cita cambiarEstado(@PathVariable Long id,
                              @RequestParam Cita.Estado estado) {
        return citaService.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
    }
}