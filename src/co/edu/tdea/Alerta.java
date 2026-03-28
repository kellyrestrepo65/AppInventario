package co.edu.tdea;

/**
 * Genera alertas cuando el stock de un producto cae bajo el minimo permitido.
 */
public class Alerta {

    /** Prefijo visible en consola para identificar las alertas. */
    private static final String PREFIJO = "[ALERTA]";

    /**
     * Verifica si la cantidad actual esta por debajo del minimo.
     *
     * @param producto producto a verificar
     * @return true si el stock esta bajo el minimo
     */
    public boolean verificarBajoMinimo(Producto producto) {
        return producto.getCantidad() < producto.getCantidadMinima();
    }

    /**
     * Imprime una alerta si el stock del producto esta bajo el minimo.
     *
     * @param producto producto a evaluar
     */
    public void generarAlerta(Producto producto) {
        if (verificarBajoMinimo(producto)) {
            System.out.println(PREFIJO + " Stock bajo en '" + producto.getNombre()
                    + "' | Actual: " + producto.getCantidad()
                    + " | Minimo: " + producto.getCantidadMinima());
        }
    }
}
