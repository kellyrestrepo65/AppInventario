package co.edu.tdea;

/**
 * Ejemplo de uso del módulo de gestión de inventario.
 */
public class Main {

    public static void main(String[] args) {
        GestorInventario gestor = new GestorInventario();

        // Registrar productos
        GestorInventario.Producto p1 = new GestorInventario.Producto("P001", "Laptop", 10, 899.99);
        GestorInventario.Producto p2 = new GestorInventario.Producto("P002", "Mouse", 50, 25.50);

        System.out.println("Registrando productos...");
        gestor.registrarProducto(p1);  // true
        gestor.registrarProducto(p2);  // true
        gestor.registrarProducto(new GestorInventario.Producto("P001", "Duplicado", 5, 10));  // false (código duplicado)
        gestor.registrarProducto(new GestorInventario.Producto("", "Sin código", 1, 5));      // false (campo vacío)
        gestor.registrarProducto(new GestorInventario.Producto("P003", "Negativo", -1, 5));   // false (valor negativo)

        // Buscar producto
        GestorInventario.Producto encontrado = gestor.buscarProductoPorCodigo("P001");
        System.out.println("\nProducto encontrado: " + encontrado);

        // Inventario completo
        System.out.println("\n--- Inventario completo ---");
        gestor.obtenerInventarioCompleto().forEach(System.out::println);

        // Movimientos
        gestor.registrarMovimiento(Movimiento.TipoMovimiento.ENTRADA, "P001", 5);
        gestor.registrarMovimiento(Movimiento.TipoMovimiento.SALIDA, "P001", 3);

        System.out.println("\n--- Historial de movimientos ---");
        gestor.obtenerHistorialMovimientos().forEach(System.out::println);

        // --- Punto 2: cola de tickets (prioridad + FIFO) ---
        System.out.println("\n=== Cola de tickets ===");
        ColaTickets cola = new ColaTickets();
        cola.insertarTicket("T1", Ticket.TIPO_NORMAL);
        cola.insertarTicket("T2", Ticket.TIPO_PRIORITARIO);
        cola.insertarTicket("T3", Ticket.TIPO_NORMAL);
        cola.insertarTicket("T4", Ticket.TIPO_PRIORITARIO);
        cola.insertarTicket("T1", Ticket.TIPO_NORMAL); // ID duplicado: no inserta
        cola.insertarTicket("T5", "URGENTE"); // tipo inválido: no inserta
        System.out.println("Orden esperado: T2, T4 (prioritarios FIFO), luego T1, T3 (normales FIFO)");
        cola.obtenerTickets().forEach(System.out::println);
    }
}
