package aplicacion;

import modelo.Torneo;
import vista.VistaTorneo;
import controlador.ControladorTorneo;

// Clase principal para ejecutar la aplicacion
public class Main {

    // Punto de entrada principal
    public static void main(String[] args) {

        // Instancia la logica 
        Torneo torneo = new Torneo();

        // Instancia la interfaz de consola
        VistaTorneo vista = new VistaTorneo();

        // Vincula el modelo con la vista mediante el controlador
        ControladorTorneo controlador = new ControladorTorneo(torneo, vista);

        // Inicia el flujo de la aplicacion
        controlador.iniciar();
    }
}