package vista;

import dao.AccidenteDaoImpl;
import service.AccidenteService;
import service.AccidenteServiceImpl;

public class ConsoleView {

    private final AccidenteServiceImpl accidenteServiceImpl;

    public ConsoleView() {
        this.accidenteServiceImpl = new AccidenteServiceImpl(new AccidenteDaoImpl());
    }

    public void iniciar(){
        System.out.println(accidenteServiceImpl.obtenerAccidentesParaAprendizaje());
    }
}
