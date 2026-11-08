
package com.barbershop.service;

import com.barbershop.entity.Cita;
import com.barbershop.entity.Servicio;
import com.barbershop.repository.CitaRepository;
import com.barbershop.repository.ServicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ServicioRepository servicioRepository;

    public CitaService(CitaRepository citaRepository,
                       ServicioRepository servicioRepository) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    public List<Cita> listarPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }

    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cita no encontrada"));
    }

    public Cita guardar(Cita cita) {

        Servicio servicio = servicioRepository.findById(
                cita.getServicio().getId()
        ).orElseThrow(() ->
                new RuntimeException("Servicio no encontrado"));

        if (!servicio.getActivo()) {
            throw new RuntimeException("El servicio no está disponible");
        }

        cita.setServicio(servicio);
        cita.setPrecio(servicio.getPrecio());
        cita.setDuracionMinutos(servicio.getDuracionMinutos());
        cita.setEstado(Cita.Estado.PROGRAMADA);

        validarDisponibilidad(cita);

        return citaRepository.save(cita);
    }

    private void validarDisponibilidad(Cita nuevaCita) {

        LocalTime inicioNuevo = nuevaCita.getHora();

        LocalTime finNuevo = inicioNuevo.plusMinutes(
                nuevaCita.getDuracionMinutos()
        );

        List<Cita> citasDelDia =
                citaRepository.findByFecha(nuevaCita.getFecha());

        for (Cita existente : citasDelDia) {

            if (existente.getEstado() == Cita.Estado.CANCELADA) {
                continue;
            }

            LocalTime inicioExistente = existente.getHora();

            LocalTime finExistente = inicioExistente.plusMinutes(
                    existente.getDuracionMinutos()
            );

            boolean seCruzan =
                    inicioNuevo.isBefore(finExistente)
                            && inicioExistente.isBefore(finNuevo);

            if (seCruzan) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ese horario ya está ocupado. Selecciona otro."
                );
            }
        }
    }

    public Cita cambiarEstado(Long id, Cita.Estado estado) {

        Cita cita = buscarPorId(id);
        cita.setEstado(estado);

        return citaRepository.save(cita);
    }

    public void eliminar(Long id) {
        Cita cita = buscarPorId(id);
        citaRepository.delete(cita);
    }
}
