package com.barbershop.service;

import com.barbershop.entity.Servicio;
import com.barbershop.repository.ServicioRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarServiciosActivos() {
        return servicioRepository.findByActivoTrue();
    }

    public Servicio buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Servicio no encontrado"));
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio datos) {
        Servicio servicio = buscarPorId(id);

        servicio.setNombre(datos.getNombre());
        servicio.setDescripcion(datos.getDescripcion());
        servicio.setPrecio(datos.getPrecio());
        servicio.setDuracionMinutos(datos.getDuracionMinutos());
        servicio.setActivo(datos.getActivo());

        return servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
        Servicio servicio = buscarPorId(id);
        servicioRepository.delete(servicio);
    }
}