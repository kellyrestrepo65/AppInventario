package co.edu.tdea;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola para el sistema de inventario.
 *
 * @author Luis Miguel
 */
public class InterfazUsuario {

    private GestorMovimiento gestorMovimiento;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Asigna el gestor de movimientos al que delega todas las operaciones.
     */
    public void setGestorMovimiento(GestorMovimiento gm) {
        this.gestorMovimiento = gm;
    }

    /**
     * Solicita al usuario el tamaño del arreglo de inventario.
     */
    public int solicitarTamanoArreglo() {
        int tamano = 0;
        boolean valido = false;

        System.out.println("Sistema de inventario");

        while (!valido) {
            try {
                System.out.print("\nIngrese el tamaño del inventario : ");
                tamano = scanner.nextInt();
                if (tamano <= 0) throw new IllegalArgumentException("El tamaño debe ser mayor a 0.");
                valido = true;
            } catch (InputMismatchException e) {
                mostrarError("Entrada inválida. Ingrese un numero entero.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                mostrarError(e.getMessage());
            }
        }
        return tamano;
    }

    /**
     * Inicia el ciclo del menú principal.
     */
    public void iniciarMenu() {
        int opcion = 0;

        do {
            mostrarMenu();
            try {
                System.out.print("\nSeleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                procesarOpcion(opcion);
            } catch (InputMismatchException e) {
                mostrarError("Opción invalida. Ingrese un numero del 1 al 5.");
                scanner.nextLine();
            }
        } while (opcion != 5);
    }

    /**
     * Muestra el menú principal.
     */
    public void mostrarMenu() {
        System.out.println(" Menú principal ");
        System.out.println(" 1. Agregar producto ");
        System.out.println(" 2. Eliminar producto ");
        System.out.println("3. Consultar inventario ");
        System.out.println("4. Registrar movimiento");
        System.out.println("5. Salir ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> opcionAgregarProducto();
            case 2 -> opcionEliminarProducto();
            case 3 -> opcionConsultarInventario();
            case 4 -> opcionRegistrarMovimiento();
            case 5 -> System.out.println("\nSaliendo del sistema.");
            default -> mostrarError("Opcion fuera de rango. Elija entre 1 y 5.");
        }
    }

    private void opcionAgregarProducto() {
        System.out.println("\n  AGREGAR PRODUCTO  ");
        try {
            System.out.print("Código del producto: ");
            String codigo = scanner.nextLine();

            System.out.print("Nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Cantidad actual: ");
            int cantidadActual = leerEntero();

            System.out.print("Cantidad mínima: ");
            int cantidadMinima = leerEntero();

            Producto producto = new Producto(codigo, nombre, cantidadActual, cantidadMinima);
            gestorMovimiento.registrarProducto(producto);
            mostrarExito("Producto agregado correctamente.");

        } catch (Exception e) {
            mostrarError("Error al agregar producto: " + e.getMessage());
        }
    }

    private void opcionEliminarProducto() {
        System.out.println("\n  ELIMINAR PRODUCTO  ");
        mostrarError("Función no disponible en esta versión.");
    }

    private void opcionConsultarInventario() {
        System.out.println("\n INVENTARIO ACTUAL");
        gestorMovimiento.consultarInventario();
    }

    private void opcionRegistrarMovimiento() {
        System.out.println("\n REGISTRAR MOVIMIENTO");
        try {
            System.out.print("Código del producto: ");
            String codigo = scanner.nextLine();

            System.out.print("Tipo (ENTRADA/SALIDA): ");
            String tipo = scanner.nextLine().toUpperCase();

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (tipo.equals("ENTRADA")) {
                gestorMovimiento.registrarEntrada(codigo, cantidad);
                mostrarExito("Entrada registrada correctamente.");
            } else if (tipo.equals("SALIDA")) {
                gestorMovimiento.registrarSalida(codigo, cantidad);
                mostrarExito("Salida registrada correctamente.");
            } else {
                mostrarError("Tipo inválido. Use ENTRADA o SALIDA.");
            }
        } catch (Exception e) {
            mostrarError("Error al registrar movimiento: " + e.getMessage());
        }
    }

    /**
     * Muestra una tabla formateada con encabezados y filas.
     */
    public void mostrarTabla(String[] encabezados, String[][] filas) {
        String separador = "-".repeat(60);
        System.out.println(separador);

        for (String enc : encabezados) {
            System.out.printf("%-15s", enc);
        }
        System.out.println();
        System.out.println(separador);

        if (filas == null || filas.length == 0) {
            System.out.println("  (Sin registros)");
        } else {
            for (String[] fila : filas) {
                for (String celda : fila) {
                    System.out.printf("%-15s", celda != null ? celda : "");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public void mostrarExito(String mensaje) {
        System.out.println("Correcto: " + mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println("ERROR: " + mensaje);
    }

    private int leerEntero() {
        while (true) {
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                mostrarError("Debe ingresar un numero entero.");
                scanner.nextLine();
            }
        }
    }

    private double leerDecimal() {
        while (true) {
            try {
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                mostrarError("Debe ingresar un numero decimal.");
                scanner.nextLine();
            }
        }
    }
}
