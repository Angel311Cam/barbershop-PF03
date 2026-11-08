
package com.barbershop;

import com.barbershop.entity.Servicio;
import com.barbershop.repository.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarServicios(ServicioRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                repository.save(new Servicio(
                        "Corte clásico",
                        "Corte tradicional de cabello",
                        new BigDecimal("50.00"),
                        30
                ));

                repository.save(new Servicio(
                        "Corte y barba",
                        "Corte de cabello y arreglo de barba",
                        new BigDecimal("80.00"),
                        45
                ));

                repository.save(new Servicio(
                        "Arreglo de barba",
                        "Perfilado y arreglo de barba",
                        new BigDecimal("35.00"),
                        20
                ));

                repository.save(new Servicio(
                        "Corte premium",
                        "Corte con acabado y estilizado",
                        new BigDecimal("65.00"),
                        40
                ));
            }
        };
    }
}
