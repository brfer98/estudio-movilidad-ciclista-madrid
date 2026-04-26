package service;

import model.Accidente;

import java.util.List;
import java.util.function.Predicate;

public interface AccidenteService {
    List<Accidente> obtenerTodosAccidentes();
    List<Accidente> obtenerAccidentesParaAprendizaje();

}
