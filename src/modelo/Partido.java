package modelo;

import java.io.Serializable;
import java.util.Objects;

// Clase modelo para representar un partido
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    // Equipos participantes
    private Equipo equipo1;
    private Equipo equipo2;

    // Etapa del torneo
    private String etapa;

    // Equipo ganador
    private Equipo ganador;


    public Partido() {
    }

    // Constructor con datos iniciales
    public Partido(Equipo equipo1, Equipo equipo2, String etapa) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.etapa = etapa;
        this.ganador = null;
    }

    // Obtiene el primer equipo
    public Equipo getEquipo1() {
        return equipo1;
    }

    // Asigna el primer equipo
    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    // Obtiene el segundo equipo
    public Equipo getEquipo2() {
        return equipo2;
    }

    // Asigna el segundo equipo
    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    // Obtiene la etapa
    public String getEtapa() {
        return etapa;
    }

    // Asigna la etapa
    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    // Obtiene el ganador
    public Equipo getGanador() {
        return ganador;
    }

    // Registra el ganador validando participacion
    public void setGanador(Equipo ganador) {
        if (ganador != null && !ganador.equals(equipo1) && !ganador.equals(equipo2)) {
            throw new IllegalArgumentException(
                "El ganador debe ser uno de los equipos participantes."
            );
        }
        this.ganador = ganador;
    }

    // Retorna la informacion  del partido en texto
    @Override
    public String toString() {
        String resultado = equipo1.getNombre() + " vs " + equipo2.getNombre();

        if (ganador != null) {
            resultado += " | Ganador: " + ganador.getNombre();
        }

        return resultado;
    }

    // Compara igualdad entre partidos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(equipo1, partido.equipo1) &&
               Objects.equals(equipo2, partido.equipo2) &&
               Objects.equals(etapa, partido.etapa) &&
               Objects.equals(ganador, partido.ganador);
    }

    // Genera el codigo
    @Override
    public int hashCode() {
        return Objects.hash(equipo1, equipo2, etapa, ganador);
    }
}