package co.edu.tdea;

import java.util.ArrayList;
import java.util.List;

/**
 * Estructura de datos para el historial de movimientos del inventario.
 */
public class ArregloMovimientos {

    private List<Movimiento> movimientos;

    public ArregloMovimientos() {
        movimientos = new ArrayList<>();
    }

    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
    }

    public List<Movimiento> obtenerMovimientos() {
        return movimientos;
    }
}