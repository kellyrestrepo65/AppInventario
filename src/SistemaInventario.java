package AppInventario.src;
public class SistemaInventario {
    
    public static void main(string[] args) {
//Crear la interfaz Usuario
        InterfazUsuario ui = new InterfazUsuario();
//Solicitar el tamaño del array
        int tamaño = ui.SolicitarTamañoArray();


        GestorInventario gestorInventario = new GestorInventario(tamaño);
        ValidadorMovimiento validadorMovimiento = new ValidadorMovimiento(gestorInventario);
        GestorMovimiento gestorMovimiento = new GestorMovimiento(validadorMovimiento, gestorInventario);





    }
}
