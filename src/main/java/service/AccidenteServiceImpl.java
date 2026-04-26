package service;

import dao.AccidenteDaoImpl;
import model.Accidente;

import java.util.List;
import java.util.function.Predicate;

public class AccidenteServiceImpl implements AccidenteService{

    private final AccidenteDaoImpl accidenteDao;
    private final Predicate<Accidente> misHoras = a -> {
        int horas = a.getHora().getHour();
        int dia = a.getFecha().getDayOfWeek().getValue();
        boolean diaLaborable = (dia <= 5 && horas >= 19 && horas <22);
        boolean finDeSemana = (dia >= 6 && horas >= 11 && horas < 22);
        return diaLaborable || finDeSemana;
    };

    public AccidenteServiceImpl(AccidenteDaoImpl accidenteDao) {
        this.accidenteDao = accidenteDao;
    }

    @Override
    public List<Accidente> obtenerTodosAccidentes() {
        return this.accidenteDao.obtenerTodosLosAccidentes();
    }

    @Override
    public List<Accidente> obtenerAccidentesParaAprendizaje() {
        return this.accidenteDao.obtenerTodosLosAccidentes()
                .stream()
                .filter(misHoras)
                .toList();
    }



}

