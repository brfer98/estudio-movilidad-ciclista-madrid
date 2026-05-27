package service;

import model.Accidente;

import java.util.List;
import java.util.function.Predicate;

public interface AccidenteService {
    List<Accidente> obtenerTodosAccidentesDeConductores();

    void analizarGravedadPorDistrito();
    void compararDistritoConMediaDeCiudad();

    void hayAccidentesGraves();
    void callesConMasAccidentes();

}
