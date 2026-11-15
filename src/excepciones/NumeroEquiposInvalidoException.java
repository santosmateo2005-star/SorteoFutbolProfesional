package excepciones;

// Excepcion personalizada para validar la cantidad de equipos
public class NumeroEquiposInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    
    public NumeroEquiposInvalidoException() {
        super();
    }

    // Constructor con mensaje de error
    public NumeroEquiposInvalidoException(String mensaje) {
        super(mensaje);
    }

    // Constructor con mensaje y causa raiz
    public NumeroEquiposInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // Constructor solo con la causa raiz
    public NumeroEquiposInvalidoException(Throwable causa) {
        super(causa);
    }
}