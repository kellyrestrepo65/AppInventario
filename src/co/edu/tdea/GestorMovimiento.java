package co.edu.tdea;

/**
 * Gestiona los movimientos de inventario (entradas y salidas).
 * Usa un arreglo de tamanio fijo para almacenar los productos.
 */
public class GestorMovimiento {

    /** Arreglo fijo de productos (tamanio definido al crear el gestor). */
    private Producto[] productos;
    /** Numero actual de productos registrados. */
    private int totalProductos;
    /** Validador de movimientos. */
    private ValidadorMovimiento validador;
    /** Generador de alertas de stock. */
    private Alerta alerta;

    /**
     * @param capacidad tamanio maximo del inventario (definido por el usuario)
     */
    public GestorMovimiento(int capacidad) {
        this.productos = new Producto[capacidad];
        this.totalProductos = 0;
        this.validador = new ValidadorMovimiento(productos, totalProductos);
        this.alerta = new Alerta();
    }

    /**
     * Registra un nuevo producto en el inventario.
     * No permite codigos duplicados ni superar la capacidad maxima.
     *
     * @param producto producto a registrar
     */
    public void registrarProducto(Producto producto) {
        if (totalProductos >= productos.length) {
            System.out.println("Error: inventario lleno.");
            return;
        }
        // Verificar codigo duplicado
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equals(producto.getCodigo())) {
                System.out.println("Error: ya existe un producto con codigo '"
                        + producto.getCodigo() + "'.");
                return;
            }
        }
        productos[totalProductos] = producto;
        totalProductos++;
        validador = new ValidadorMovimiento(productos, totalProductos);
        System.out.println("Producto registrado: " + producto);
    }

    /**
     * Registra una entrada de inventario. Incrementa la cantidad del producto.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad a ingresar (debe ser mayor que cero)
     */
    public void registrarEntrada(String codigo, int cantidad) {
        if (!validador.validarEntrada(codigo, cantidad)) {
            return;
        }
        Producto p = validador.buscarProducto(codigo);
        p.setCantidad(p.getCantidad() + cantidad);
        System.out.println("Entrada: " + codigo + " | +" + cantidad
                + " | Stock actual: " + p.getCantidad());
        alerta.generarAlerta(p);
    }

    /**
     * Registra una salida de inventario. Decrementa la cantidad del producto.
     *
     * @param codigo   codigo del producto
     * @param cantidad cantidad a retirar (no puede dejar el stock negativo)
     */
    public void registrarSalida(String codigo, int cantidad) {
        if (!validador.validarSalida(codigo, cantidad)) {
            return;
        }
        Producto p = validador.buscarProducto(codigo);
        p.setCantidad(p.getCantidad() - cantidad);
        System.out.println("Salida:  " + codigo + " | -" + cantidad
                + " | Stock actual: " + p.getCantidad());
        alerta.generarAlerta(p);
    }

    /**
     * Muestra todos los productos registrados en el inventario.
     */
    public void consultarInventario() {
        System.out.println("--- Inventario ---");
        if (totalProductos == 0) {
            System.out.println("Sin productos registrados.");
            return;
        }
        for (int i = 0; i < totalProductos; i++) {
            System.out.println(productos[i]);
        }
    }
}
