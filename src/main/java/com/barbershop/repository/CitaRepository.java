
package com.barbershop.repository;

import com.barbershop.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByEstado(Cita.Estado estado);

    List<Cita> findByFechaAndEstado(LocalDate fecha, Cita.Estado estado);
}
