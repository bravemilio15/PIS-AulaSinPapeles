/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Tutoria;

/**
 *
 * @author walter
 */
public class TutoriaDao extends AdaptadorDAO<Tutoria> {

    private Tutoria tutoria;


    public TutoriaDao() {
        super(Tutoria.class);
    }

    public Tutoria getTutoria() {
        if (this.tutoria == null) {
            this.tutoria = new Tutoria();
        }
        return tutoria;
    }

    public void setTutoria(Tutoria tutoria) {
        this.tutoria = tutoria;
    }

    public void guardar() throws IOException {
        tutoria.setId(generateID());
        this.guardar(tutoria);
    }

    public void modificar(Integer pos) throws EmptyException, PositionException, IOException {
        this.modificar(tutoria, pos);
    }



    private Integer generateID() {
        return listar().size() + 1;
    }

}
