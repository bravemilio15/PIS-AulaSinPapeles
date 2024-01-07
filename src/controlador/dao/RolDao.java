/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Rol;

/**
 *
 * @author santiago
 */
public class RolDao extends AdaptadorDAO<Rol> {

    private Rol rol;

    public RolDao() {
        super(Rol.class);
    }

    public Rol getRol() {
        if (this.rol == null) {
            this.rol = new Rol();
        }
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void guardar() throws IOException {
        rol.setId(generateID());
        this.guardar(rol);
    }

    public void modificar(Integer pos) throws EmptyException, PositionException, IOException {
        this.modificar(rol, pos);
    }

    private Integer generateID() {
        return generarId();
    }
    
    public Rol obtenerRolPorNombre(String nombreRol) throws EmptyException, PositionException {
        ListaEnlazada<Rol> roles = listar();

        for (int i = 0; i < roles.size(); i++) {
            Rol rol = roles.get(i);
            if (rol.getNombre_rol().equalsIgnoreCase(nombreRol)) {
                return rol;
            }
        }

        return null; // Retornar null si no se encuentra el rol con el nombre proporcionado
    }

    public static void main(String[] args) throws IOException {
        RolDao cd = new RolDao();
        cd.getRol().setNombre_rol("Docente");
        cd.getRol().setDescripccion("Rol del Docente");
        cd.guardar();

    }

}
