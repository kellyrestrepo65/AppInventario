package co.edu.tdea;

/**
 * Punto de entrada principal del sistema de inventario.
 * Integra todos los modulos: UI, gestor de movimientos y validaciones.
 *
 * @author Luis Miguel
 */
public class SistemaInventario {

    public static void main(String[] args) {

        InterfazUsuario ui = new InterfazUsuario();

        int tamano = ui.solicitarTamanoArreglo();

        GestorMovimiento gestorMovimiento = new GestorMovimiento(tamano);

        ui.setGestorMovimiento(gestorMovimiento);

        ui.iniciarMenu();
    }
}
