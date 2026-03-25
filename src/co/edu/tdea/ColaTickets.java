package co.edu.tdea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cola de tickets con prioridad: PRIORITARIO primero, NORMAL después; FIFO dentro de cada tipo.
 */
public class ColaTickets {

    private final List<Ticket> tickets;

    public ColaTickets() {
        tickets = new ArrayList<>();
    }

    /**
     * Inserta un ticket validando ID único y tipo (PRIORITARIO o NORMAL).
     * Ubicación: al final del bloque de prioritarios (o al inicio si solo hay normales), o al final si es NORMAL.
     *
     * @return true si se insertó correctamente
     */
    public boolean insertarTicket(String id, String tipo) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("El ID no puede ser nulo ni vacío.");
            return false;
        }
        String idTrim = id.trim();

        if (tipo == null || tipo.trim().isEmpty()) {
            System.out.println("El tipo no puede ser nulo ni vacío.");
            return false;
        }
        String tipoNorm = tipo.trim();

        if (!Ticket.TIPO_PRIORITARIO.equals(tipoNorm) && !Ticket.TIPO_NORMAL.equals(tipoNorm)) {
            System.out.println("El tipo debe ser \"" + Ticket.TIPO_PRIORITARIO + "\" o \"" + Ticket.TIPO_NORMAL + "\".");
            return false;
        }

        for (Ticket t : tickets) {
            if (idTrim.equals(t.getId())) {
                System.out.println("Ya existe un ticket con ese ID.");
                return false;
            }
        }

        Ticket nuevo = new Ticket(idTrim, tipoNorm);
        int posicion = calcularPosicionInsercion(tipoNorm);
        tickets.add(posicion, nuevo);
        return true;
    }

    /**
     * Calcula el índice donde debe ir el nuevo ticket manteniendo prioridad y FIFO.
     */
    private int calcularPosicionInsercion(String tipo) {
        if (tickets.isEmpty()) {
            return 0;
        }

        if (Ticket.TIPO_NORMAL.equals(tipo)) {
            // Los normales van al final (después de todos los prioritarios y normales existentes).
            return tickets.size();
        }

        // PRIORITARIO: después del último prioritario existente (FIFO entre prioritarios).
        int i = 0;
        while (i < tickets.size() && tickets.get(i).esPrioritario()) {
            i++;
        }
        return i;
    }

    public List<Ticket> obtenerTickets() {
        return Collections.unmodifiableList(tickets);
    }

    public int cantidad() {
        return tickets.size();
    }
}
