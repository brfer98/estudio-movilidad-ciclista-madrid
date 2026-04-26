package model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@ToString
public class Accidente {

    private final String numExpediente;

    private final LocalDate fecha; // FECHA
    private final LocalTime hora; // RANGO HORARIO

    private final String localizacion; // LUGAR ACCIDENTE
    private final String numeroDireccion;
    private final Integer codigoDistrito;
    private final String distrito; // DISTRITO
    private final String tipoAccidente; // TIPO ACCIDENTE

    private final String estadoMeteorologico;

    private final String tipoVehiculo; // TIPO VEHICULO
    private final String tipoPersona; // TIPO PERSONA
    private final String rangoEdad; // TRAMO EDAD
    private final String sexo;

    private final Integer codigoLesividad;
    private final String lesividad; // LESIVIDAD

    private final Integer coordenadaXUtm;
    private final Integer coordenadaYUtm;

    private final String positivoAlcohol;
    private final Integer positivoDroga;

    public Accidente(String numExpediente, LocalDate fecha, LocalTime hora, String localizacion, String numeroDireccion, Integer codigoDistrito, String distrito, String tipoAccidente, String estadoMeteorologico, String tipoVehiculo, String tipoPersona, String rangoEdad, String sexo, Integer codigoLesividad, String lesividad, Integer coordenadaXUtm, Integer coordenadaYUtm, String positivoAlcohol, Integer positivoDroga) {
        this.numExpediente = numExpediente;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion = localizacion;
        this.numeroDireccion = numeroDireccion;
        this.codigoDistrito = codigoDistrito;
        this.distrito = distrito;
        this.tipoAccidente = tipoAccidente;
        this.estadoMeteorologico = estadoMeteorologico;
        this.tipoVehiculo = tipoVehiculo;
        this.tipoPersona = tipoPersona;
        this.rangoEdad = rangoEdad;
        this.sexo = sexo;
        this.codigoLesividad = codigoLesividad;
        this.lesividad = lesividad;
        this.coordenadaXUtm = coordenadaXUtm;
        this.coordenadaYUtm = coordenadaYUtm;
        this.positivoAlcohol = positivoAlcohol;
        this.positivoDroga = positivoDroga;
    }
}
