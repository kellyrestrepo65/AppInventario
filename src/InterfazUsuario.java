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
                System.out.print("Ingrese el tamaño del inventario: ")
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
            System.out.print(\n"Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine();
            procesarOpcion(opcion);
        } catch (InputMismatchException e) {
            mostrarError("Opción invalida. Ingrese un numero del 1 al 5.");
            scanner.nextline();
        }
    } while (opcion != 5);
}


public void mostrarMenu() {
    System.out.println(" Menú principal ")

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

}