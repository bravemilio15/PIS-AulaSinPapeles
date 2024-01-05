/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Bravo
 */
public class Tutoria {

    private Integer id;
    private String fecha;
    private String temaGeneral;
    private Boolean estado;
    private Boolean asistencia;
    private Paralelo paralelo;
    private String Descripcion;

    public Tutoria() {
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTemaGeneral() {
        return temaGeneral;
    }

    public void setTemaGeneral(String temaGeneral) {
        this.temaGeneral = temaGeneral;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

}
