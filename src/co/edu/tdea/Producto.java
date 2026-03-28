package co.edu.tdea;

/**
 * Representa un producto del inventario.
 * Contiene codigo, nombre, cantidad actual y cantidad minima.
 *
 * @author Juan Camilo
 */
public class Producto {

    public String codigo;
    public String nombre;
    public int cantidadActual;
    public int cantidadMinima;

    public Producto(String codigo, String nombre, int cantidadActual, int cantidadMinima) {
        setCodigo(codigo);
        setNombre(nombre);
        setCantidadActual(cantidadActual);
        setCantidadMinima(cantidadMinima);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    /**
     * Alias de getCantidadActual(). Requerido por GestorMovimiento y ValidadorMovimiento.
     */
    public int getCantidad() {
        return cantidadActual;
    }

    /**
     * Alias de setCantidadActual(). Requerido por GestorMovimiento y ValidadorMovimiento.
     */
    public void setCantidad(int cantidad) {
        setCantidadActual(cantidad);
    }

    public void setCodigo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            this.codigo = codigo;
        } else {
            throw new IllegalArgumentException("El codigo no puede estar vacio");
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
    }

    public void setCantidadActual(int cantidadActual) {
        if (cantidadActual >= 0) {
            this.cantidadActual = cantidadActual;
        } else {
            throw new IllegalArgumentException("La cantidad actual no puede ser negativa");
        }
    }

    public void setCantidadMinima(int cantidadMinima) {
        if (cantidadMinima >= 0) {
            this.cantidadMinima = cantidadMinima;
        } else {
            throw new IllegalArgumentException("La cantidad minima no puede ser negativa");
        }
    }

    public boolean estaBajoMinimo() {
        return cantidadActual < cantidadMinima;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "codigo='" + codigo + '\''
                + ", nombre='" + nombre + '\''
                + ", cantidadActual=" + cantidadActual
                + ", cantidadMinima=" + cantidadMinima
                + '}';
    }
}
