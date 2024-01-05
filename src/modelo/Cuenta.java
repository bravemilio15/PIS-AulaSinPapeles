package modelo;

public class Cuenta {

    private Integer id;
    private String contra;
    private String nombre_usuario;
    private Integer id_estudiante;
    private Integer id_docente;

    public Integer getId_docente() {
        return id_docente;
    }

    public void setId_docente(Integer id_docente) {
        this.id_docente = id_docente;
    }
    
    

    public Integer getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(Integer id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

}
