package dao;

import model.Accidente;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class AccidenteDaoImpl implements AccidenteDao{

    private static final Path ARCHIVO = Path.of("data/300110-0-accidentes-bicicleta-csv.csv");

    @Override
    public List<Accidente> obtenerTodosLosAccidentes() {

        List<Accidente> accidentes = new ArrayList<>();

        if (!Files.exists(ARCHIVO)){
            return List.of();
        }

        try {
            List<String> lineas = Files.readAllLines(ARCHIVO);

            // cabeceras
            String[] cabeceras = lineas.getFirst().split(";");

            // mapear indices
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < cabeceras.length; i++) {
                map.put(cabeceras[i], i);
            }

            return lineas.stream()
                    .skip(1)
                    .map(l -> crearAccidente(l, map))
                    .toList();

        } catch (IOException e) {
            System.err.println("Error en la lectura del archivo");
            return List.of();
        }
    }


    private Accidente crearAccidente(String linea, Map<String, Integer> map){
        String[] data = linea.split(";", -1);
        return Accidente.builder()
                .numExpediente(extraerTexto(data, map, "num_expediente"))
                .fecha(java.time.LocalDate.parse(extraerTexto(data, map, "fecha")))
                .hora(java.time.LocalTime.parse(extraerTexto(data, map, "hora")))
                .localizacion(extraerTexto(data, map, "localizacion"))
                .numeroDireccion(extraerEntero(data, map, "numero"))
                .codigoDistrito(extraerEntero(data, map, "cod_distrito"))
                .distrito(extraerTexto(data, map, "distrito"))
                .tipoAccidente(extraerTexto(data, map, "tipo_accidente"))
                .estadoMeteorologico(extraerTexto(data, map, "estado_meteorologico"))
                .tipoVehiculo(extraerTexto(data, map, "tipo_vehiculo"))
                .tipoPersona(extraerTexto(data, map, "tipo_persona"))
                .rangoEdad(extraerTexto(data, map, "rango_edad"))
                .sexo(extraerTexto(data, map, "sexo"))
                .codigoLesividad(extraerEntero(data, map, "cod_lesividad"))
                .lesividad(extraerTexto(data, map, "lesividad"))
                .coordenadaXUtm(extraerEntero(data, map, "coordenada_x_utm"))
                .coordenadaYUtm(extraerEntero(data, map, "coordenada_y_utm"))
                .positivoAlcohol(extraerTexto(data, map, "positiva_alcohol"))
                .positivoDroga(extraerEntero(data, map, "positiva_droga"))
                .build();
    }


    private String extraerTexto(String[] data, Map<String, Integer> map, String columna){
        Integer indice = map.get(columna);
        return data[indice];
    }

    private Integer extraerEntero(String[] data, Map<String, Integer> map, String columna) {
        String texto = extraerTexto(data, map, columna);
        return Integer.parseInt(texto);
    }






}