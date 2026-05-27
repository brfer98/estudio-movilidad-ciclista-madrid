package model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class Accidente {
    private final String numExpediente;

    private final LocalDate fecha;
    private final LocalTime hora;

    private final String localizacion;
    private final String numeroDireccion;
    private final Integer codigoDistrito;
    private final String distrito;
    private final String tipoAccidente;

    private final String estadoMeteorologico;

    private final String tipoVehiculo;
    private final String tipoPersona;
    private final String rangoEdad;
    private final String sexo;

    private final Integer codigoLesividad;
    private final String lesividad;

    private final Integer coordenadaXUtm;
    private final Integer coordenadaYUtm;

    private final String positivoAlcohol;
    private final Integer positivoDroga;
}
