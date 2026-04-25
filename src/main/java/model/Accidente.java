package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Accidente {

    private final String expediente;

    private final LocalDate fecha; // FECHA
    private final LocalTime hora; // RANGO HORARIO

    private final String localizacion; // LUGAR ACCIDENTE
    private final Integer numeroDireccion;
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

    private final Boolean positivoAlcohol;
    private final Integer positivoDroga;

    /******************/

    private final String diaSemana;
    private final Integer numero;
    private final String numeroParte;
    private final String estadoPavimento;
    private final Integer numeroVictimas;

    public Accidente(String expediente, LocalDate fecha, LocalTime hora, String localizacion, Integer numeroDireccion, Integer numero, Integer codigoDistrito, String distrito, String tipoAccidente, String estadoMeteorologico, String tipoVehiculo, String tipoPersona, String rangoEdad, String sexo, Integer codigoLesividad, String lesividad, Integer coordenadaXUtm, Integer coordenadaYUtm, Boolean positivoAlcohol, Integer positivoDroga, String diaSemana, String numeroParte, String estadoPavimento, Integer numeroVictimas) {
        this.expediente = expediente;
        this.fecha = fecha;
        this.hora = hora;
        this.localizacion = localizacion;
        this.numeroDireccion = numeroDireccion;
        this.numero = numero;
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
        this.diaSemana = diaSemana;
        this.numeroParte = numeroParte;
        this.estadoPavimento = estadoPavimento;
        this.numeroVictimas = numeroVictimas;
    }
}
