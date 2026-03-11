
public class ArregloProductos {

    private Producto[] productos;
    private int contador;

    /**
     * Constructor que define el tamaño fijo del arreglo.
     *
     * @param capacidad Tamaño máximo del arreglo
     */
    public ArregloProductos(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        productos = new Producto[capacidad];
        contador = 0;
    }

    /**
     * Agrega un producto si hay espacio disponible y si el codigo no está
     * repetido.
     */
    public boolean agregarProducto(Producto producto) {
        if (contador >= productos.length) {
            return false; // Arreglo lleno
        }

        if (buscarProducto(producto.getCodigo()) != null) {
            return false; // Codigo duplicado
        }

        productos[contador] = producto;
        contador++;
        return true;
    }

    /**
     * Busca un producto por su codigo.
     */
    public Producto buscarProducto(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    /**
     * Obtiene un producto por indice.
     */
    public Producto obtenerPorIndice(int indice) {
        if (indice >= 0 && indice < contador) {
            return productos[indice];
        }
        throw new IndexOutOfBoundsException("Indice fuera de rango");
    }

    /**
     * Retorna la cantidad actual de productos almacenados.
     */
    public int getCantidadActual() {
        return contador;
    }

    /**
     * Retorna la capacidad total del arreglo.
     */
    public int getCapacidad() {
        return productos.length;
    }
}
