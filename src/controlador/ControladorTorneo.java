package controlador;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Equipo;
import modelo.Partido;
import modelo.Torneo;
import vista.VistaTorneo;

import excepciones.NumeroEquiposInvalidoException;
import excepciones.SorteoInvalidoException;

// Controlador principal para gestionar el flujo del torneo
public class ControladorTorneo {

    private Torneo torneo;
    private final VistaTorneo vista;

    // Constructor que asigna modelo y vista
    public ControladorTorneo(Torneo torneo, VistaTorneo vista) {
        this.torneo = torneo;
        this.vista = vista;
    }

    // Inicia el ciclo del menu principal
    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            int opcion = vista.mostrarMenu();

            switch (opcion) {
                case 1:
                    realizarOctavos();
                    break;
                case 2:
                    realizarCuartos();
                    break;
                case 3:
                    realizarSemifinales();
                    break;
                case 4:
                    realizarFinal();
                    break;
                case 5:
                    mostrarResultados();
                    break;
                case 6:
                    guardarTorneo();
                    break;
                case 7:
                    cargarTorneo();
                    break;
                case 0:
                    vista.mostrarMensaje("Gracias por utilizar el sistema.");
                    continuar = false;
                    break;
                default:
                    vista.mostrarMensaje("Opcion no valida. Intente nuevamente.");
                    break;
            }
        }
    }

    // Ejecuta el sorteo para la fase de octavos
    private void realizarOctavos() {
        try {
            torneo.sortearOctavos();
            vista.mostrarMensaje("Sorteo de octavos realizado correctamente.");
            vista.mostrarPartidos("Octavos de final", torneo.getPartidosOctavos());
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarMensaje("ERROR: " + e.getMessage());
        }
    }

    // Registra ganadores de octavos y sortea cuartos
    private void realizarCuartos() {
        try {
            registrarGanadores(torneo.getPartidosOctavos());
            torneo.sortearCuartos();
            vista.mostrarMensaje("Sorteo de cuartos realizado correctamente.");
            vista.mostrarPartidos("Cuartos de final", torneo.getPartidosCuartos());
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarMensaje("ERROR: " + e.getMessage());
        }
    }

    // Registra ganadores de cuartos y sortea semifinales
    private void realizarSemifinales() {
        try {
            registrarGanadores(torneo.getPartidosCuartos());
            torneo.sortearSemifinales();
            vista.mostrarMensaje("Sorteo de semifinales realizado correctamente.");
            vista.mostrarPartidos("Semifinales", torneo.getPartidosSemifinales());
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarMensaje("ERROR: " + e.getMessage());
        }
    }

    // Registra ganadores de semifinales y sortea la final
    private void realizarFinal() {
        try {
            registrarGanadores(torneo.getPartidosSemifinales());
            torneo.sortearFinal();
            vista.mostrarMensaje("Sorteo de la final realizado correctamente.");
            vista.mostrarPartidos("FINAL", torneo.getPartidosFinal());
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarMensaje("ERROR: " + e.getMessage());
        }
    }

    // Solicita y asigna los ganadores a cada partido
    private void registrarGanadores(ArrayList<Partido> partidos) throws SorteoInvalidoException {
        if (partidos == null || partidos.isEmpty()) {
            throw new SorteoInvalidoException("Primero debe realizar el sorteo de la etapa anterior.");
        }

        for (Partido partido : partidos) {
            int opcion = vista.solicitarGanador(partido);
            Equipo ganador = (opcion == 1) ? partido.getEquipo1() : partido.getEquipo2();
            torneo.registrarGanador(partido, ganador);
        }
    }

    // Muestra la lista de partidos de todas las etapas
    private void mostrarResultados() {
        vista.mostrarPartidos("Octavos de final", torneo.getPartidosOctavos());
        vista.mostrarPartidos("Cuartos de final", torneo.getPartidosCuartos());
        vista.mostrarPartidos("Semifinales", torneo.getPartidosSemifinales());
        vista.mostrarPartidos("Final", torneo.getPartidosFinal());
    }

    // Pide el nombre de archivo y serializa el torneo
    private void guardarTorneo() {
        try {
            String nombreArchivo = vista.solicitarNombreArchivo();
            torneo.guardarTorneo(nombreArchivo);
            vista.mostrarMensaje("Torneo guardado correctamente en: " + nombreArchivo);
        } catch (IOException e) {
            vista.mostrarMensaje("ERROR DE ARCHIVO: " + e.getMessage());
        }
    }

    // Pide el nombre de archivo y deserializa el torneo
    private void cargarTorneo() {
        try {
            String nombreArchivo = vista.solicitarNombreArchivo();
            torneo = Torneo.cargarTorneo(nombreArchivo);
            vista.mostrarMensaje("Torneo recuperado correctamente desde: " + nombreArchivo);
            mostrarResultados();
        } catch (IOException e) {
            vista.mostrarMensaje("ERROR DE ARCHIVO: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarMensaje("ERROR: No se pudo reconocer el objeto guardado.");
        }
    }
}