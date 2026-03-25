package co.edu.tdea;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor principal de inventario. Lógica de negocio y validaciones.
 */
public class GestorInventario {

    private List<Producto> productos;
    private ArregloMovimientos arregloMovimientos;

    public GestorInventario() {
        productos = new ArrayList<>();
        arregloMovimientos = new ArregloMovimientos();
    }

    /**
     * Representa un producto del inventario (clase interna).
     */
    public static class Producto {

        private String codigo;
        private String nombre;
        private int cantidad;
        private double precio;

        public Producto(String codigo, String nombre, int cantidad, double precio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        @Override
        public String toString() {
            return "Codigo: " + codigo +
                    " Nombre: " + nombre +
                    " Cantidad: " + cantidad +
                    " Precio: " + precio;
        }
    }

    /**
     * Registra un producto con validaciones:
     * - Códigos únicos
     * - Campos vacíos o nulos
     * - Valores negativos
     * Registra movimiento ENTRADA en el historial.
     */
    public boolean registrarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("El producto no puede ser nulo.");
            return false;
        }

        if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            System.out.println("El código no puede estar vacío.");
            return false;
        }

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }

        if (producto.getCantidad() < 0 || producto.getPrecio() < 0) {
            System.out.println("Cantidad o precio no pueden ser negativos.");
            return false;
        }

        if (buscarProductoPorCodigo(producto.getCodigo()) != null) {
            System.out.println("Ya existe un producto con ese código.");
            return false;
        }

        productos.add(producto);

        Movimiento movimiento = new Movimiento(
                Movimiento.TipoMovimiento.ENTRADA,
                producto.getCodigo(),
                producto.getCantidad()
        );
        arregloMovimientos.agregarMovimiento(movimiento);

        return true;
    }

    /**
     * Busca un producto por su código.
     */
    public Producto buscarProductoPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        for (Producto p : productos) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Retorna el inventario completo como lista.
     */
    public List<Producto> obtenerInventarioCompleto() {
        return new ArrayList<>(productos);
    }

    /**
     * Retorna el historial de movimientos.
     */
    public List<Movimiento> obtenerHistorialMovimientos() {
        return arregloMovimientos.obtenerMovimientos();
    }

    /**
     * Registra un movimiento (ENTRADA o SALIDA) y actualiza el stock.
     */
    public void registrarMovimiento(Movimiento.TipoMovimiento tipo, String codigo, int cantidad) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("Código inválido.");
            return;
        }

        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        if (cantidad <= 0) {
            System.out.println("Cantidad inválida.");
            return;
        }

        if (tipo == Movimiento.TipoMovimiento.SALIDA && producto.getCantidad() < cantidad) {
            System.out.println("No hay suficiente stock.");
            return;
        }

        if (tipo == Movimiento.TipoMovimiento.ENTRADA) {
            producto.setCantidad(producto.getCantidad() + cantidad);
        } else {
            producto.setCantidad(producto.getCantidad() - cantidad);
        }

        Movimiento movimiento = new Movimiento(tipo, codigo, cantidad);
        arregloMovimientos.agregarMovimiento(movimiento);
    }
}
