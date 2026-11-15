package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import excepciones.NumeroEquiposInvalidoException;
import excepciones.SorteoInvalidoException;

// Clase principal para gestionar la lógica del torneo y su persistencia
public class Torneo implements Serializable {

    private static final long serialVersionUID = 1L;

    // Nombre de archivo por defecto para guardar y cargar el torneo
    private static final String ARCHIVO_DEFECTO = "torneo.ser";

    // Lista general de equipos participantes
    private ArrayList<Equipo> equipos;

    // Listas de partidos por cada fase
    private ArrayList<Partido> partidosOctavos;
    private ArrayList<Partido> partidosCuartos;
    private ArrayList<Partido> partidosSemifinales;
    private ArrayList<Partido> partidosFinal;

    // Constructor que inicializa las listas y carga los equipos
    public Torneo() {
        equipos = new ArrayList<>();
        partidosOctavos = new ArrayList<>();
        partidosCuartos = new ArrayList<>();
        partidosSemifinales = new ArrayList<>();
        partidosFinal = new ArrayList<>();

        inicializarEquipos();
    }

    // Inicializa la lista base con los 16 equipos ecuatorianos
    private void inicializarEquipos() {
        equipos.add(new Equipo("Independiente del Valle"));
        equipos.add(new Equipo("Liga de Quito"));
        equipos.add(new Equipo("Barcelona SC"));
        equipos.add(new Equipo("Emelec"));
        equipos.add(new Equipo("El Nacional"));
        equipos.add(new Equipo("Universidad Católica"));
        equipos.add(new Equipo("SD Aucas"));
        equipos.add(new Equipo("Delfín SC"));
        equipos.add(new Equipo("CD Macará"));
        equipos.add(new Equipo("Técnico Universitario"));
        equipos.add(new Equipo("Deportivo Cuenca"));
        equipos.add(new Equipo("Orense SC"));
        equipos.add(new Equipo("Mushuc Runa"));
        equipos.add(new Equipo("Imbabura SC"));
        equipos.add(new Equipo("Libertad FC"));
        equipos.add(new Equipo("Cuniburo FC"));
    }

    // Retorna una copia de la lista de equipos
    public ArrayList<Equipo> getEquipos() {
        return new ArrayList<>(equipos);
    }

    // Retorna copia de los partidos de octavos
    public ArrayList<Partido> getPartidosOctavos() {
        return new ArrayList<>(partidosOctavos);
    }

    // Retorna copia de los partidos de cuartos
    public ArrayList<Partido> getPartidosCuartos() {
        return new ArrayList<>(partidosCuartos);
    }

    // Retorna copia de los partidos de semifinales
    public ArrayList<Partido> getPartidosSemifinales() {
        return new ArrayList<>(partidosSemifinales);
    }

    // Retorna copia de los partidos de la final
    public ArrayList<Partido> getPartidosFinal() {
        return new ArrayList<>(partidosFinal);
    }

    // Valida que no existan equipos nulos, sin nombre o duplicados
    private void validarEquipos(ArrayList<Equipo> lista) throws SorteoInvalidoException {
        Set<String> nombres = new HashSet<>();

        for (Equipo equipo : lista) {
            if (equipo == null) {
                throw new SorteoInvalidoException("Existe un equipo nulo en la lista.");
            }

            String nombre = equipo.getNombre();
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new SorteoInvalidoException("Existe un equipo sin nombre.");
            }

            String nombreNormalizado = nombre.trim().toLowerCase();
            if (!nombres.add(nombreNormalizado)) {
                throw new SorteoInvalidoException("El equipo " + nombre + " se encuentra repetido.");
            }
        }
    }

    // Prepara las validaciones e inicia el sorteo de una etapa
    public ArrayList<Partido> sortearPartidos(ArrayList<Equipo> equiposEtapa, String etapa)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {

        if (equiposEtapa == null) {
            throw new NumeroEquiposInvalidoException("La lista de equipos no puede ser nula.");
        }

        int cantidad = equiposEtapa.size();

        if (cantidad < 2 || (cantidad & (cantidad - 1)) != 0) {
            throw new NumeroEquiposInvalidoException(
                "Cantidad de equipos invalida: " + cantidad + ". Debe ser 2, 4, 8, 16, etc."
            );
        }

        if (etapa == null || etapa.trim().isEmpty()) {
            throw new SorteoInvalidoException("La etapa del torneo no puede estar vacia.");
        }

        validarEquipos(equiposEtapa);

        ArrayList<Equipo> disponibles = new ArrayList<>(equiposEtapa);
        ArrayList<Partido> resultado = new ArrayList<>();
        Random aleatorio = new Random();

        sorteoRecursivo(disponibles, etapa, resultado, aleatorio);

        return resultado;
    }

    // Algoritmo recursivo para emparejar los equipos de forma aleatoria
    private void sorteoRecursivo(ArrayList<Equipo> disponibles, String etapa,
                                 ArrayList<Partido> partidos, Random aleatorio)
            throws SorteoInvalidoException {

        if (disponibles.isEmpty()) {
            return;
        }

        if (disponibles.size() < 2 || disponibles.size() % 2 != 0) {
            throw new SorteoInvalidoException("No es posible completar el emparejamiento.");
        }

        int indice1 = aleatorio.nextInt(disponibles.size());
        Equipo equipo1 = disponibles.remove(indice1);

        int indice2 = aleatorio.nextInt(disponibles.size());
        Equipo equipo2 = disponibles.remove(indice2);

        if (equipo1 == equipo2 || equipo1.getNombre().equalsIgnoreCase(equipo2.getNombre())) {
            throw new SorteoInvalidoException("Un equipo no puede enfrentarse a si mismo.");
        }

        Partido nuevoPartido = new Partido(equipo1, equipo2, etapa);
        partidos.add(nuevoPartido);

        sorteoRecursivo(disponibles, etapa, partidos, aleatorio);
    }

    // Genera el sorteo de octavos de final
    public void sortearOctavos() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        partidosOctavos = sortearPartidos(equipos, "Octavos de final");
    }

    // Extrae y valida los ganadores de una etapa finalizada
    private ArrayList<Equipo> obtenerGanadores(ArrayList<Partido> partidos, String etapa)
            throws SorteoInvalidoException {

        if (partidos == null || partidos.isEmpty()) {
            throw new SorteoInvalidoException("Primero debe realizarse el sorteo de " + etapa + ".");
        }

        ArrayList<Equipo> ganadores = new ArrayList<>();

        for (Partido partido : partidos) {
            if (partido == null || partido.getGanador() == null) {
                throw new SorteoInvalidoException(
                    "Todos los partidos de " + etapa + " deben tener un ganador registrado."
                );
            }
            ganadores.add(partido.getGanador());
        }

        validarEquipos(ganadores);
        return ganadores;
    }

    // Genera el sorteo de cuartos de final con los ganadores de octavos
    public void sortearCuartos() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        ArrayList<Equipo> ganadores = obtenerGanadores(partidosOctavos, "octavos");
        partidosCuartos = sortearPartidos(ganadores, "Cuartos de final");
    }

    // Genera el sorteo de semifinales con los ganadores de cuartos
    public void sortearSemifinales() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        ArrayList<Equipo> ganadores = obtenerGanadores(partidosCuartos, "cuartos");
        partidosSemifinales = sortearPartidos(ganadores, "Semifinales");
    }

    // Genera el sorteo de la final con los ganadores de semifinales
    public void sortearFinal() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        ArrayList<Equipo> ganadores = obtenerGanadores(partidosSemifinales, "semifinales");
        partidosFinal = sortearPartidos(ganadores, "Final");
    }

    // Verifica si un partido pertenece a alguna fase del torneo
    private boolean partidoRegistrado(Partido partido) {
        return partidosOctavos.contains(partido)
            || partidosCuartos.contains(partido)
            || partidosSemifinales.contains(partido)
            || partidosFinal.contains(partido);
    }

    // Asigna el ganador 
    public void registrarGanador(Partido partido, Equipo ganador) throws SorteoInvalidoException {
        if (partido == null || !partidoRegistrado(partido)) {
            throw new SorteoInvalidoException("El partido no pertenece a este torneo.");
        }

        try {
            partido.setGanador(ganador);
        } catch (IllegalArgumentException e) {
            throw new SorteoInvalidoException("No se pudo registrar el ganador: " + e.getMessage());
        }
    }

    // Guarda el estado del torneo en un archivo especifico
    public void guardarTorneo(String nombreArchivo) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(this);
        }
    }

    // Guarda el estado del torneo usando el archivo por defecto torneo.ser
    public void guardarTorneo() throws IOException {
        guardarTorneo(ARCHIVO_DEFECTO);
    }

    // Carga un objeto Torneo desde un archivo especifico
    public static Torneo cargarTorneo(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            Object objeto = entrada.readObject();

            if (!(objeto instanceof Torneo)) {
                throw new IOException("El archivo no contiene un torneo valido.");
            }

            return (Torneo) objeto;
        }
    }

    // Carga un objeto Torneo usando el archivo por defecto torneo.ser
    public static Torneo cargarTorneo() throws IOException, ClassNotFoundException {
        return cargarTorneo(ARCHIVO_DEFECTO);
    }
}