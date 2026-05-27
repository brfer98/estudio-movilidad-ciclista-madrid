package service;

import dao.AccidenteDaoImpl;
import model.Accidente;

import javax.xml.transform.stax.StAXResult;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

public class AccidenteServiceImpl implements AccidenteService{

    private static final String MI_DISTRITO = "PUENTE DE VALLECAS";
    private static final int HORA_MIN_SEMANA = 19;
    private static final int HORA_MAX_SEMANA = 22;
    private static final int HORA_MIN_FINDE = 11;
    private static final int HORA_MAX_FINDE = 22;
    private static final int ULTIMO_DIA_SEMANA = 5;
    private static final int PRIMER_DIA_FINDE = 6;

    private static final String TIPO_CONDUCTOR = "Conductor";
    private static final String ACCIDENTE_GRAVE = "Ingreso superior a 24 horas";
    private static final String TIPO_VEHICULO = "Bicicleta";

    private static final int MAX_CALLES = 3;

    public static final Consumer<String> IMPRIMIR_REPORTE = System.out::println;

    public static final Supplier<String> TITULO_GRAVEDAD = () -> "\n---------- ANÁLISIS DE GRAVEDAD POR DISTRITO ----------";
    public static final Supplier<String> TITULO_MEDIA = () -> "\n---------- COMPARATIVA CON OTROS DISTRITOS USANDO LAS MISMAS CONDICIONES ----------";
    public static final Supplier<String> TITULO_VERIFICACION = () -> "\n---------- VERIFICACION DE GRAVEDAD EN MI ZONA ----------";
    public static final Supplier<String> TITULO_CALLES = () -> "\n ---------- CALLES CON MAS ACCIDENTES ----------";

    public static final UnaryOperator<String> FORMATEAR_CALLE = String::toUpperCase;

    public static final Function<Accidente, String > EXTRAER_LOCALIZACION = Accidente::getLocalizacion;

    private final AccidenteDaoImpl accidenteDao;

    // filtra datos -> true - false en funcion de las variables que ponga
    private final Predicate<Accidente> misHoras = a -> {
        int horas = a.getHora().getHour();
        int dia = a.getFecha().getDayOfWeek().getValue();
        boolean diaLaborable = (dia <= ULTIMO_DIA_SEMANA && horas >= HORA_MIN_SEMANA && horas < HORA_MAX_SEMANA);
        boolean finDeSemana = (dia >= PRIMER_DIA_FINDE && horas >= HORA_MIN_FINDE && horas < HORA_MAX_FINDE);
        return diaLaborable || finDeSemana;
    };
    // elegir solo registros de conductores
    private final Predicate<Accidente> esConductor = a -> a.getTipoPersona() != null && a.getTipoPersona().equalsIgnoreCase(TIPO_CONDUCTOR);
    // elegir solo bicicletas
    private final Predicate<Accidente> esBicicleta = a -> a.getTipoVehiculo() != null && a.getTipoVehiculo().equalsIgnoreCase(TIPO_VEHICULO);
    private final Predicate<Accidente> esMiDistrito = a -> a.getDistrito().equalsIgnoreCase(MI_DISTRITO);
    private final Predicate<Accidente> esGrave = a -> a.getLesividad() != null && a.getLesividad().equalsIgnoreCase(ACCIDENTE_GRAVE);

    public AccidenteServiceImpl(AccidenteDaoImpl accidenteDao) {
        this.accidenteDao = accidenteDao;
    }

    @Override
    public List<Accidente> obtenerTodosAccidentesDeConductores() {
        return this.accidenteDao.obtenerTodosLosAccidentes().stream()
                .filter(esConductor)
                .filter(esBicicleta)
                .toList();
    }


    @Override
    public void analizarGravedadPorDistrito() {

        List<Accidente> accidentesEnMisHoras = obtenerTodosAccidentesDeConductores().stream().filter(misHoras).toList();
        Map<String, List<Accidente>> accidentePorDistrito = accidentesEnMisHoras.stream().collect(Collectors.groupingBy(Accidente::getDistrito));

        IMPRIMIR_REPORTE.accept(TITULO_GRAVEDAD.get());

        accidentePorDistrito.forEach((distrito, accidentes) -> {
           int total = accidentes.size();
           long accidentesGraves = accidentes
                   .stream()
                   .filter(a -> a.getLesividad() != null && a.getLesividad().equalsIgnoreCase(ACCIDENTE_GRAVE))
                   .count();

           // porcentaje accidentes graves / accidentes totales
            double porcentajeAccidentesGraves = (double) (accidentesGraves * 100) / total;
            String resultado = String.format("Distrito: %-20s | Total: %4d | Accidentes graves: %5.2f%%",distrito, total, porcentajeAccidentesGraves);


            IMPRIMIR_REPORTE.accept(resultado);
        });
    }

    @Override
    public void compararDistritoConMediaDeCiudad() {

        // todos los accidentes ocurridos en mis horas
        List<Accidente> accidentes = obtenerTodosAccidentesDeConductores()
                .stream()
                .filter(misHoras)
                .toList();

        // cantidad accidentes en mis horas en Madrid / cada uno de los distritos unicos que tienen accidentes en mis horas
        double mediaAccidentesPorDistrito = (double) accidentes.size() / contarDistritos(accidentes);

        IMPRIMIR_REPORTE.accept(TITULO_MEDIA.get());
        IMPRIMIR_REPORTE.accept(String.format("Media de Madrid: %.2f accidentes/distrito", mediaAccidentesPorDistrito));
        IMPRIMIR_REPORTE.accept(String.format("%s: %d accidentes", MI_DISTRITO, accidentesEnMiDistrito(accidentes)));

        // compara los accidentes ocurridos en mi horario
        if (accidentesEnMiDistrito(accidentes) < mediaAccidentesPorDistrito) {
            IMPRIMIR_REPORTE.accept(String.format("%s es más seguro que la media", MI_DISTRITO));
        } else {
            IMPRIMIR_REPORTE.accept(String.format("%s supera la media de siniestralidad", MI_DISTRITO));
        }
    }

    @Override
    public void hayAccidentesGraves() {
        List<Accidente> accidentesEnMisHorasYDistrito = obtenerAccidentesEnHorasYDistrito();

        // "true" si ninguno cumple la condicion
        boolean ceroAccidentesGraves = accidentesEnMisHorasYDistrito.stream().noneMatch(esGrave);

        IMPRIMIR_REPORTE.accept(TITULO_VERIFICACION.get());

        if (ceroAccidentesGraves) {
            IMPRIMIR_REPORTE.accept("No hay registro de accidentes graves (Ingreso > 24h) en mis horas");
        } else {
            IMPRIMIR_REPORTE.accept("Se ha detectado al menos un accidente grave en mis franjas horarias");

            // se obtiene el primero
            accidentesEnMisHorasYDistrito.stream()
                    .filter(esGrave)
                    .findFirst()
                    .ifPresent(a -> {
                        String fechaFormateada = a.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        IMPRIMIR_REPORTE.accept(
                                String.format("Detalle del primer riesgo encontrado: Accidente grave registrado en %s el dia %s a las %s",
                                        a.getLocalizacion(), fechaFormateada, a.getHora())
                        );
                    });
        }
    }

    @Override
    public void callesConMasAccidentes() {
        List<Accidente> misAccidentes = obtenerAccidentesEnHorasYDistrito();
        IMPRIMIR_REPORTE.accept(TITULO_CALLES.get());

        if (misAccidentes.isEmpty()) {
            IMPRIMIR_REPORTE.accept("No hay datos suficientes");
            return;
        }

        misAccidentes.stream()
                .map(EXTRAER_LOCALIZACION)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(MAX_CALLES)
                .map(e -> FORMATEAR_CALLE.apply(e.getKey() + " (" + e.getValue() + " accidentes)"))
                .forEach(IMPRIMIR_REPORTE);
    }

    private List<Accidente> obtenerAccidentesEnHorasYDistrito() {
        return this.obtenerTodosAccidentesDeConductores()
                .stream()
                .filter(misHoras)
                .filter(esMiDistrito)
                .toList();
    }

    // funcion para contar cuantos distritos unicos hay
    private long contarDistritos(List<Accidente> accidentes) {
        return accidentes.stream().map(Accidente::getDistrito).distinct().count();
    }

    private long accidentesEnMiDistrito(List<Accidente> accidentes) {
        // return obtenerAccidentesEnHorasYDistrito().size();
        return accidentes.stream()
                .filter(esMiDistrito)
                .count();
    }


}

