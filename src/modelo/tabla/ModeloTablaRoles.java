package modelo.tabla;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import javax.swing.table.AbstractTableModel;
import modelo.Rol;

public class ModeloTablaRoles extends AbstractTableModel {

    private ListaEnlazada<Rol> datos;

    public ListaEnlazada<Rol> getDatos() {
        return datos;
    }

    public void setDatos(ListaEnlazada<Rol> datos) {
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
            Rol e = datos.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return e.getId();
                case 1:
                    return e.getNombre_rol();
                case 2:
                    return e.getDescripccion();

 
            }
        } catch (EmptyException | PositionException ex) {
            System.out.println("Error en tablaRols: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // Ajustado al número de atributos en Rol
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Nombre";
            case 2:
                return "Descripcion";
        }

        return null;
    }
}
