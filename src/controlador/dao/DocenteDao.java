/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Docente;

/**
 *
 * @author cristian
 */
public class DocenteDao extends AdaptadorDAO<Docente> {

    private Docente docente;
    //Docente 

    public DocenteDao() {
        super(Docente.class);
    }

    public Docente getDocente() {
        if (this.docente == null) {
            this.docente = new Docente();
        }
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void guardar() throws IOException {
        docente.setId(generateID());
        super.guardar(docente); // 
    }

    public void modificar(Integer pos) throws EmptyException, PositionException, IOException {
        super.modificar(docente, pos);
    }

    public Integer obtenerId_DocentePorCorreoCedula(String correoInst, String cedula)
            throws EmptyException, PositionException {
        ListaEnlazada<Docente> roles = listar();

        for (int i = 0; i < roles.size(); i++) {
            Docente rol = roles.get(i);
            if (rol.getCorreoIns().equalsIgnoreCase(correoInst)
                    && rol.getCedula().equalsIgnoreCase(cedula)) {

                return rol.getId();
            }
        }

        return null; 
    }

    public Integer generateID() {
        return generarId(); 
    }

}
