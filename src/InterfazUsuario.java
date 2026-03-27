package AppInventario.src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfazUsuario {

    private GestorInventario gestorInventario;
    private GestorMovimiento gestorMovimiento;
    private ValidadorMovimiento validador;

    private final Scanner scanner = new Scanner(System.in);


    public void setGestores(GestorInventario gi, GestorMovimiento gm, ValidadorMovimiento v) {
        this.gestorInventario = gi;
        this.gestorMovimiento = gm;
        this.validador = v;
    }

    public int solicitarTamanoArreglo() {
        int tamano = 0;
        boolean valido = false;

        System.out.println("Sistema de inventario");


        while (!valido) {
            try {
                System.out.print("\nIngrese el tamaño del inventario : ");
             tamano = scanner.nextInt();
             if (tamano <=0 ) throw new IllegalArgumentException("El tamaño debe ser mayor a 0.");
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


public void mostrarMenu() {
    System.out.println(" Menú principal ");

    System.out.println(" 1. Agregar producto ");
    System.out.println(" 2. Eliminar producto ");
    System.out.println( "3. Consultar inventario ");
    System.out.println( "4. Registrar movimiento");
    System.out.println( "5. Salir ");
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
        System.out.println("Nombre del producto: ");
        
        String nombre = scanner.nextLine();

        System.out.println("Cantidad: ");

        int cantidad = leerEntero();

        System.out.println("Precio:  ");

        double precio = leerDecimal();

        gestorInventario.agregarProducto(nombre, cantidad, precio);
        mostrarExito("Producto agregado correctamente. ");

        } catch (Exception e)  {
            mostrarError("Error al agregar producto: " + e.getMessage());
        }
    }
    
    private void opcionEliminarProducto() {
        System.out.println("\n  ELIMINAR PRODUCTO  ");

        try {
            System.out.print("Índice del producto a eliminar: ");
            int indice = leerEntero();

            gestorInventario.eliminarProducto(indice);

            mostrarExito("Producto eliminado correctamente. ");
        } catch (Exception e) {
            mostrarError("Error al eliminar producto: " + e.getMessage());
        }
    }

    
    private void opcionConsultarInventario() {
        System.out.println("\n INVENTARIO ACTUAL");

        String[][] datos = gestorInventario.obtenerInventario();
        mostrarTabla(new String[]{"#", "Nombre", "Cantidad", "Precio"}, datos);
    } 
    
    private void opcionRegistrarMovimiento() {
        System.out.println("\n REGISTRAR MOVIMIENTO");
        try {
            System.out.print("Índice del producto: ");

            int indice = leerEntero();

            System.out.println("Tipo (ENTRADA/SALIDA): ");

            String tipo = scanner.nextLine().toUpperCase();

            System.out.print("Cantidad:  ");

            int cantidad = leerEntero();

            if(!validador.esMovimientoValido(indice, tipo, cantidad)) {
                mostrarError("Movimiento no valido. Verifique los datos ingresados. ");
                return;
            }

            gestorMovimiento.registrarMovimiento(indice, tipo, cantidad);
            mostrarExito("Movimiento registrado correctamente.");
        } catch (Exception e) {
            mostrarError("Error al registrar movimiento: " + e.getMessage());
        }
    }

    public void mostrarTabla(String[] encabezados, String[][] filas) {
        String separador = "-".repeat(60);
        System.out.println(separador);

        for (String enc : encabezados) {
            System.out.printf("%-15s", enc);
        }
        System.out.println();
        System.out.println(separador);

        //Filas
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
    System.out.println(("Correcto" + mensaje));
}
public void mostrarError(String mensaje) {
    System.err.println("ERROR" + mensaje);
}

private int leerEntero() {
    while (true) {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine();
            return valor;
        } catch (InputMismatchException e ) {
            mostrarError("Debe ingresar un numero entero. ");
            scanner.nextLine();
        }
    }
}

    private double leerDecimal() {
        while (true) {
            try{
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                mostrarError("Debe ingresar un numero decimal. ");
                scanner.nextLine();
            }
        }
    }
}  