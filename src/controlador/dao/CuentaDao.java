/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Cuenta;

/**
 *
 * @author JOSTIN
 */
public class CuentaDao extends AdaptadorDAO<Cuenta> {

    private Cuenta cuenta;

    public CuentaDao() {
        super(Cuenta.class);
    }

    public Cuenta getCuenta() {
        if (this.cuenta == null) {
            this.cuenta = new Cuenta();
        }
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void guardar() throws IOException {
        cuenta.setId(generateID());
        this.guardar(cuenta);
    }

    public void modificar(Integer pos) throws EmptyException, PositionException, IOException {
        this.modificar(cuenta, pos);
    }

    private Integer generateID() {
        return listar().size() + 1;
    }
    
    

    public static void main(String[] args) throws IOException {
        CuentaDao cd = new CuentaDao();
        cd.getCuenta().setNombre_usuario("pepito");
        cd.getCuenta().setContra("si");
        cd.guardar();
    }

}
