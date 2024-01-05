package modelo.tabla;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import javax.swing.table.AbstractTableModel;
import modelo.Tutoria;

public class ModeloTablaTutorias extends AbstractTableModel {

    private ListaEnlazada<Tutoria> datos;

    public ListaEnlazada<Tutoria> getDatos() {
        return datos;
    }

    public void setDatos(ListaEnlazada<Tutoria> datos) {
        this.datos = datos;
    }

    @Override
    public int getRowCount() {
        if (datos == null) {
            System.out.println("¡La lista de datos es nula!");
            return 0;
        }
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (datos == null) {
            System.out.println("¡La lista de datos es nula!");
            return null;
        }

        try {
            Tutoria e = datos.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return e.getId();
                case 1:
                    return e.getEstado();
                case 2:
                    return e.getParalelo().getNombre_paralelo();
                case 3:
                    return e.getDescripcion();

            }
        } catch (EmptyException | PositionException ex) {
            System.out.println("Error en tablaTutorias: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // Ajustado al número de atributos en Tutoria
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Estado";
            case 2:
                return "Paralelo";
            case 3:
                return "Tema General";
        }

        return null;
    }
}
