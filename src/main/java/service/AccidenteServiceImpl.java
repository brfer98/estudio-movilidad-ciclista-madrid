package service;

import dao.AccidenteDaoImpl;
import model.Accidente;

import java.util.List;

public class AccidenteServiceImpl implements AccidenteService{

    private final AccidenteDaoImpl accidenteDao;

    public AccidenteServiceImpl(AccidenteDaoImpl accidenteDao) {
        this.accidenteDao = accidenteDao;
    }

    @Override
    public List<Accidente> obtenerTodosAccidentes() {
        return this.accidenteDao.obtenerTodosLosAccidentes();
    }
}

