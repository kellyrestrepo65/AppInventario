# AppInventario

Sistema de gestión de inventario por consola desarrollado en Java como proyecto colaborativo.

---

## Estructura del proyecto

Todos los archivos se encuentran en el paquete `co.edu.tdea`:

```
src/co/edu/tdea/
├── Producto.java              (Juan Camilo)
├── ArregloProductos.java      (Juan Camilo)
├── Movimiento.java            (Emanuel)
├── ArregloMovimientos.java    (Emanuel)
├── GestorInventario.java      (Emanuel)
├── Alerta.java                (Kelly)
├── ValidadorMovimiento.java   (Kelly)
├── GestorMovimiento.java      (Kelly)
├── Ticket.java                (extra)
├── ColaTickets.java           (extra)
├── InterfazUsuario.java       (Luis Miguel)
├── SistemaInventario.java     (Luis Miguel)
└── Main.java                  (pruebas backend)
```

---

## Puntos de entrada

| Clase | Descripción |
|-------|-------------|
| `co.edu.tdea.SistemaInventario` | Menú interactivo por consola (aplicación principal) |
| `co.edu.tdea.Main` | Prueba automática del backend sin interacción |

---

## Menú principal (SistemaInventario)

```
1. Agregar producto
2. Eliminar producto   (no disponible en esta versión)
3. Consultar inventario
4. Registrar movimiento (ENTRADA / SALIDA)
5. Salir
```

Al iniciar, el sistema solicita el tamaño máximo del inventario (arreglo de tamaño fijo).

---

## Responsables por clase

### Juan Camilo — Modelo de dominio
- **`Producto.java`**: atributos `codigo`, `nombre`, `cantidadActual`, `cantidadMinima`. Getters/setters con validaciones. Método `estaBajoMinimo()`.
- **`ArregloProductos.java`**: arreglo de tamaño fijo. Métodos `agregarProducto()`, `buscarProducto()`, `obtenerPorIndice()`.

### Emanuel — Lógica de negocio
- **`GestorInventario.java`**: registra productos (valida duplicados, nulos, negativos), busca por código, retorna inventario completo e historial de movimientos.
- **`Movimiento.java`**: registra tipo (`ENTRADA`/`SALIDA`), código de producto, cantidad y timestamp automático.
- **`ArregloMovimientos.java`**: lista dinámica del historial de movimientos.

### Kelly — Operaciones y validaciones
- **`ValidadorMovimiento.java`**: valida cantidad > 0, existencia del código, stock suficiente y que no quede negativo.
- **`GestorMovimiento.java`**: gestiona arreglo fijo de productos. Métodos `registrarEntrada()` y `registrarSalida()` con validaciones integradas.
- **`Alerta.java`**: genera alerta por consola cuando el stock cae por debajo del mínimo permitido.

### Luis Miguel — Interfaz de usuario
- **`InterfazUsuario.java`**: menú interactivo, entrada/salida de datos, manejo de excepciones, visualización formateada.
- **`SistemaInventario.java`**: clase `main`, orquesta todos los módulos.

---

## Ajustes de integración realizados

Al consolidar el trabajo de los cuatro integrantes se encontraron los siguientes problemas y se aplicaron los cambios mínimos necesarios para que la aplicación funcione:

### 1. Unificación de paquetes

**Problema:** Los archivos estaban distribuidos en tres paquetes distintos:
- `default` (sin paquete): `Producto.java`, `ArregloProductos.java`
- `AppInventario.src`: `GestorInventario.java`, `GestorMovimiento.java`, `ValidadorMovimiento.java`, `InterfazUsuario.java`, `SistemaInventario.java`
- `co.edu.tdea`: resto de clases

**Solución:** Se movieron todos los archivos al paquete `co.edu.tdea` agregando la declaración `package co.edu.tdea;` a `Producto.java` y `ArregloProductos.java`, y actualizando el paquete de `InterfazUsuario.java` y `SistemaInventario.java`.

---

### 2. Eliminación de clases duplicadas (stubs)

**Problema:** Existían tres clases duplicadas en `AppInventario.src` con implementaciones vacías (stubs) que repetían nombres de clases ya implementadas en `co.edu.tdea`:

| Archivo eliminado | Razón |
|-------------------|-------|
| `src/GestorInventario.java` | Stub vacío, reemplazado por la implementación completa en `co.edu.tdea` |
| `src/GestorMovimiento.java` | Stub vacío, reemplazado por la implementación completa en `co.edu.tdea` |
| `src/ValidadorMovimiento.java` | Stub vacío, reemplazado por la implementación completa en `co.edu.tdea` |

---

### 3. Métodos alias en Producto.java

**Problema:** El código de Kelly (`GestorMovimiento`, `ValidadorMovimiento`, `Alerta`) llama a `getCantidad()` y `setCantidad()`, pero Juan nombró esos métodos `getCantidadActual()` y `setCantidadActual()`.

**Solución:** Se agregaron dos métodos alias en `Producto.java` sin cambiar la lógica existente:

```java
// Alias requerido por GestorMovimiento, ValidadorMovimiento y Alerta
public int getCantidad() {
    return cantidadActual;
}

public void setCantidad(int cantidad) {
    setCantidadActual(cantidad);
}
```

---

### 4. Adaptación de InterfazUsuario y SistemaInventario

**Problema:** Luis diseñó la UI contra los stubs vacíos de `AppInventario.src`, los cuales tenían una API completamente diferente a las implementaciones reales:

| Llamada original (stub) | Implementación real disponible |
|-------------------------|-------------------------------|
| `gestorInventario.agregarProducto(nombre, cantidad, precio)` | `gestorMovimiento.registrarProducto(Producto)` |
| `gestorInventario.eliminarProducto(indice)` | No implementado |
| `gestorInventario.obtenerInventario()` → `String[][]` | `gestorMovimiento.consultarInventario()` |
| `validador.esMovimientoValido(indice, tipo, cantidad)` | `validador.validarEntrada/Salida(codigo, cantidad)` (interno en GestorMovimiento) |
| `gestorMovimiento.registrarMovimiento(indice, tipo, cantidad)` | `registrarEntrada(codigo, cantidad)` / `registrarSalida(codigo, cantidad)` |
| `new GestorInventario(tamano)` | `new GestorMovimiento(tamano)` |
| `new ValidadorMovimiento(gestorInventario)` | `new ValidadorMovimiento(Producto[], int)` (interno) |

**Solución:** Se reescribieron `InterfazUsuario.java` y `SistemaInventario.java` para conectarse a `GestorMovimiento` (Kelly), que es la clase que integra internamente el validador y las alertas. Se conservó toda la estructura del menú, los métodos helper (`leerEntero`, `mostrarError`, `mostrarExito`, `mostrarTabla`) y el manejo de excepciones.

La opción "Eliminar producto" se mantiene en el menú pero muestra un mensaje de no disponible, ya que ninguna implementación del equipo incluyó esa funcionalidad.

---

## Ejecución por línea de comandos

Requiere tener instalado el JDK (Java Development Kit). Verificar con:

```bash
java -version
javac -version
```

Desde la raíz del proyecto (`AppInventario/`):

```bash
# 1. Crear carpeta de salida si no existe
mkdir -p bin

# 2. Compilar todos los archivos
javac -d bin src/co/edu/tdea/*.java

# 3. Ejecutar la aplicación interactiva
java -cp bin co.edu.tdea.SistemaInventario

# 4. (Opcional) Ejecutar pruebas del backend
java -cp bin co.edu.tdea.Main
```

---

## Ejecución en Eclipse

1. Abrir Eclipse y seleccionar **File > Open Projects from File System...**
2. Hacer clic en **Directory...** y seleccionar la carpeta `AppInventario`
3. Hacer clic en **Finish**
4. En el panel **Package Explorer**, expandir `src > co.edu.tdea`
5. Para ejecutar la app interactiva: clic derecho sobre `SistemaInventario.java` > **Run As > Java Application**
6. Para ejecutar las pruebas del backend: clic derecho sobre `Main.java` > **Run As > Java Application**
7. La consola aparece en la pestaña **Console** en la parte inferior

> **Nota:** Si Eclipse muestra errores de compilación al abrir, clic derecho sobre el proyecto > **Build Path > Configure Build Path** y verificar que el JDK esté configurado en la pestaña **Libraries**.

---

## Ejecución en IntelliJ IDEA

1. Abrir IntelliJ y seleccionar **Open**
2. Navegar hasta la carpeta `AppInventario` y hacer clic en **OK**
3. IntelliJ detectará automáticamente los archivos fuente. Si pide configurar el SDK, ir a **File > Project Structure > Project** y seleccionar el JDK instalado
4. Esperar a que termine la indexación del proyecto
5. En el panel **Project**, expandir `src > co > edu > tdea`
6. Para ejecutar la app interactiva: clic derecho sobre `SistemaInventario.java` > **Run 'SistemaInventario.main()'**
7. Para ejecutar las pruebas del backend: clic derecho sobre `Main.java` > **Run 'Main.main()'**
8. La consola aparece en la pestaña **Run** en la parte inferior

> **Nota:** Si el directorio `src` no aparece en azul (source root), clic derecho sobre la carpeta `src` > **Mark Directory as > Sources Root**.

---

## Ejecución en NetBeans

1. Abrir NetBeans y seleccionar **File > New Project...**
2. Elegir categoría **Java with Ant** > **Java Application** y hacer clic en **Next**
3. Asignar un nombre al proyecto, desmarcar **Create Main Class** y hacer clic en **Finish**
4. En el panel **Projects**, clic derecho sobre la carpeta `Source Packages` > **New > Java Package**, escribir `co.edu.tdea`
5. Copiar todos los archivos `.java` de `src/co/edu/tdea/` dentro del paquete creado
6. Clic derecho sobre `SistemaInventario.java` > **Run File** para la app interactiva
7. Clic derecho sobre `Main.java` > **Run File** para las pruebas

**Alternativa (importar carpeta existente):**
1. **File > Open Project...**
2. Seleccionar la carpeta `AppInventario` (NetBeans la reconoce si contiene un archivo `.project` de Eclipse o un `build.xml` de Ant)
3. Si no la reconoce, usar la opción **New Project** de arriba

> **Nota:** La consola interactiva de NetBeans acepta entrada del usuario en la pestaña **Output** en la parte inferior.

---

## Ejecución en Visual Studio Code

**Requisitos previos:**
- Tener instalada la extensión **Extension Pack for Java** (publicada por Microsoft). Instalarla desde el panel de extensiones (`Ctrl+Shift+X`) buscando `Extension Pack for Java`.

**Pasos:**
1. Abrir VS Code y seleccionar **File > Open Folder...**, luego seleccionar la carpeta `AppInventario`
2. VS Code detectará automáticamente los archivos Java y configurará el proyecto
3. En el panel **Explorer**, expandir `src > co > edu > tdea`
4. Abrir `SistemaInventario.java`
5. Hacer clic en el botón **Run** (triángulo verde) que aparece encima del método `main`, o presionar `F5`
6. La terminal integrada se abrirá en la parte inferior y mostrará la consola del programa

> **Nota importante:** La app usa `Scanner` para leer del teclado. Asegurarse de que la terminal donde se ejecuta sea interactiva. En VS Code, si se ejecuta con `F5` (modo debug), usar mejor la opción **Run Without Debugging** (`Ctrl+F5`) para que la terminal acepte entrada del usuario correctamente.

> Si aparece el error `Main method not found`, verificar que VS Code reconoce `src` como source root: debe aparecer el ícono de carpeta azul en el panel Explorer.
