package co.edu.tdea;

/**
 * Valida las operaciones de movimiento sobre el inventario.
 * Trabaja directamente con el arreglo de productos.
 */
public class ValidadorMovimiento {

    /** Arreglo de productos del inventario. */
    private Producto[] productos;
    /** Numero actual de productos registrados. */
    private int totalProductos;

    /**
     * @param productos       arreglo de productos del inventario
     * @param totalProductos  cantidad de productos registrados
     */
    public ValidadorMovimiento(Producto[] productos, int totalProductos) {
        this.productos = productos;
        this.totalProductos = totalProductos;
    }

    /**
     * Valida que la cantidad sea mayor que cero.
     *
     * @param cantidad cantidad a validar
     * @return true si es valida
     */
    public boolean validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            System.out.println("Error: la cantidad debe ser mayor que cero.");
            return false;
        }
        return true;
    }

    /**
     * Busca un producto por codigo en el arreglo.
     *
     * @param codigo codigo del producto
     * @return el producto encontrado o null
     */
    public Producto buscarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    /**
     * Valida que el codigo exista en el inventario.
     *
     * @param codigo codigo a verificar
     * @return true si existe
     */
    public boolean validarCodigoExiste(String codigo) {
        if (buscarProducto(codigo) == null) {
            System.out.println("Error: no existe producto con codigo '" + codigo + "'.");
            return false;
        }
        return true;
    }

    /**
     * Valida que haya stock suficiente para una salida.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad solicitada
     * @return true si hay stock suficiente
     */
    public boolean validarStockSuficiente(String codigo, int cantidad) {
        Producto p = buscarProducto(codigo);
        if (p.getCantidad() < cantidad) {
            System.out.println("Error: stock insuficiente. Disponible: "
                    + p.getCantidad() + ", solicitado: " + cantidad + ".");
            return false;
        }
        return true;
    }

    /**
     * Valida que la salida no deje el stock en negativo.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad a retirar
     * @return true si el resultado no es negativo
     */
    public boolean validarNoNegativo(String codigo, int cantidad) {
        Producto p = buscarProducto(codigo);
        if ((p.getCantidad() - cantidad) < 0) {
            System.out.println("Error: la operacion dejaria el stock en negativo.");
            return false;
        }
        return true;
    }

    /**
     * Ejecuta todas las validaciones para una entrada.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad a ingresar
     * @return true si pasa todas las validaciones
     */
    public boolean validarEntrada(String codigo, int cantidad) {
        return validarCantidad(cantidad) && validarCodigoExiste(codigo);
    }

    /**
     * Ejecuta todas las validaciones para una salida.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad a retirar
     * @return true si pasa todas las validaciones
     */
    public boolean validarSalida(String codigo, int cantidad) {
        return validarCantidad(cantidad)
                && validarCodigoExiste(codigo)
                && validarStockSuficiente(codigo, cantidad)
                && validarNoNegativo(codigo, cantidad);
    }
}
