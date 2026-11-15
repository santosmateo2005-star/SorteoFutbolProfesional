package excepciones;

// Excepcion personalizada para errores en el sorteo del torneo
public class SorteoInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;


    public SorteoInvalidoException() {
        super();
    }

    // Constructor con mensaje de error
    public SorteoInvalidoException(String mensaje) {
        super(mensaje);
    }

    // Constructor con mensaje y causa raiz
    public SorteoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // Constructor solo con la causa raiz
    public SorteoInvalidoException(Throwable causa) {
        super(causa);
    }
}