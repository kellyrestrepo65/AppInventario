package AppInventario.src;
public class SistemaInventario {
    
    public static void main(String[] args) {

        InterfazUsuario ui = new InterfazUsuario();

        int tamano =  ui.solicitarTamanoArreglo();

        GestorInventario gestorInventario = new GestorInventario(tamano);

        ValidadorMovimiento validador = new ValidadorMovimiento(gestorInventario);

        GestorMovimiento gestorMovimiento = new GestorMovimiento(gestorInventario, validador);

        ui.setGestores(gestorInventario, gestorMovimiento, validador);

        ui.iniciarMenu();




    }
}
