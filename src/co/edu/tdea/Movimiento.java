package co.edu.tdea;

import java.time.LocalDateTime;

/**
 * Representa un registro en el historial de movimientos del inventario.
 */
public class Movimiento {

    public enum TipoMovimiento {
        ENTRADA,
        SALIDA
    }

    private TipoMovimiento tipoMovimiento;
    private String codigoProducto;
    private int cantidad;
    private LocalDateTime fechaTimestamp;

    public Movimiento(TipoMovimiento tipoMovimiento, String codigoProducto, int cantidad) {
        this.tipoMovimiento = tipoMovimiento;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.fechaTimestamp = LocalDateTime.now();
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaTimestamp() {
        return fechaTimestamp;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "tipo=" + tipoMovimiento +
                ", codigoProducto='" + codigoProducto + '\'' +
                ", cantidad=" + cantidad +
                ", fechaTimestamp=" + fechaTimestamp +
                '}';
    }
}