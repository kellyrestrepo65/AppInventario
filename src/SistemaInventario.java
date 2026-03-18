package AppInventario.src;
public class SistemaInventario {
    
    public static void main(string[] args) {

        InterfazUsuario ui = new InterfazUsuario();

        int tamano =  ui.solicitarTamanoArreglo();

        GestorInventario gestorInventario = new GestorInventario(tamano);

        ValidadorMovimiento validador = new ValidadorMovimiento()

        GestorMovimiento(gestorInventario, validador);

        ui.setGestores(gestorInventario, gestorMovimiento, validador);

        ui.iniciarMenu();




    }
}
