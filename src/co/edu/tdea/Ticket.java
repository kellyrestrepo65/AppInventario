package co.edu.tdea;

/**
 * Representa un ticket en la cola (punto 2 del taller).
 */
public class Ticket {

    public static final String TIPO_PRIORITARIO = "PRIORITARIO";
    public static final String TIPO_NORMAL = "NORMAL";

    private final String id;
    private final String tipo;

    public Ticket(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean esPrioritario() {
        return TIPO_PRIORITARIO.equals(tipo);
    }

    @Override
    public String toString() {
        return "Ticket{id='" + id + "', tipo='" + tipo + "'}";
    }
}
