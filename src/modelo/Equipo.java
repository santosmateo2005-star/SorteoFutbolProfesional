package modelo;

import java.io.Serializable;
import java.util.Objects;

// Clase modelo para el equipo
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributo principal
    private String nombre;


    public Equipo() {
    }

    // Constructor con parametros
    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    // Obtiene el nombre
    public String getNombre() {
        return nombre;
    }

    // Asigna el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Retorna el nombre como texto
    @Override
    public String toString() {
        return nombre;
    }

    // Compara igualdad entre objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(nombre, equipo.nombre);
    }

    // Genera el codigo
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}