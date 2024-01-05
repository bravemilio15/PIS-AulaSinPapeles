/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Estudiante;

/**
 *
 * @author cristian
 */
public class EstudianteDao extends AdaptadorDAO<Estudiante> {

    private Estudiante estudiante;

    public EstudianteDao() {
        super(Estudiante.class);
    }

    public Estudiante getEstudiante() {
        if (this.estudiante == null) {
            this.estudiante = new Estudiante();
        }
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void guardar() throws IOException {
        estudiante.setId(generateID());
        this.guardar(estudiante);
    }

    public void modificar(Integer pos) throws EmptyException, PositionException, IOException {
        this.modificar(estudiante, pos);
    }

    private Integer generateID() {
        return listar().size() + 1;
    }

    public Integer obtenerId_EstudiantePorCorreoCedula(String correoInst, String cedula) 
            throws EmptyException, PositionException {
        ListaEnlazada<Estudiante> roles = listar();

        for (int i = 0; i < roles.size(); i++) {
            Estudiante rol = roles.get(i);
            if (rol.getCorreoIns().equalsIgnoreCase(correoInst)
                    && rol.getCedula().equalsIgnoreCase(cedula)) {
                
              
                return rol.getId();
            }
        }

        return null; // Retornar null si no se encuentra el rol con el nombre proporcionado
    }

}
