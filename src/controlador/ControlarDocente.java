package controlador;

import controlador.dao.DocenteDao;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Docente;
import modelo.Materia;

public class ControlarDocente {

    private ListaEnlazada<Docente> docentes;
    private DocenteDao docenteDao;
    private Docente docente;

    public ControlarDocente() {
        this.docenteDao = new DocenteDao();
        this.docentes = docenteDao.listar();
    }

    public ListaEnlazada<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(ListaEnlazada<Docente> docentes) {
        this.docentes = docentes;
    }

    public DocenteDao getDocenteDao() {
        return docenteDao;
    }

    public void setDocenteDao(DocenteDao docenteDao) {
        this.docenteDao = docenteDao;
    }

    public ListaEnlazada<Docente> listar() {
        return docenteDao.listar();
    }

    public Docente getDocente() {
        if (docente == null) {
            docente = new Docente();
        }
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Boolean registrar(ListaEnlazada<Materia> todasLasMaterias) throws EmptyException, PositionException {
        if (docente == null) {
            docente = new Docente();
        }

        docente.setId(docentes.size() + 1);
        addMateriaToDocente(todasLasMaterias);
        docentes.insertar(docente);
        guardarDao();
        return true;
    }

    public void addMateriaToDocente(ListaEnlazada<Materia> todasLasMaterias)
            throws EmptyException, PositionException {
        ListaEnlazada<Materia> materiasDocente = docente.getMaterias();

        for (int i = 0; i < todasLasMaterias.size(); i++) {
            Materia materia = todasLasMaterias.get(i);

            if (materiasDocente.contiene(materia)) {
                continue;
            }

            materiasDocente.insertar(materia);
        }
    }

    public Boolean registrarDocente() {
        if (docente == null) {
            docente = new Docente();
        }

        docente.setMaterias(new ListaEnlazada<>());
        docente.setId(docentes.size() + 1);
        docentes.insertar(docente);
        guardarDao();
        return true;
    }

    private Materia buscarMateriaPorId(ListaEnlazada<Materia> materias, Integer id)
            throws EmptyException, PositionException {
        for (int i = 0; i < materias.size(); i++) {
            Materia materia = materias.get(i);
            if (materia.getId().equals(id)) {
                return materia;
            }
        }
        return null;
    }

    private Materia buscarMateriaPorNombre(ListaEnlazada<Materia> materias, String nombre)
            throws EmptyException, PositionException {
        for (int i = 0; i < materias.size(); i++) {
            Materia materia = materias.get(i);
            if (materia.getNombre().equals(nombre)) {
                return materia;
            }
        }
        return null;
    }

    public void guardarDao() {
        try {
            docenteDao.guardar(docente);

            // Imprime un mensaje indicando que la operación de guardar se realizó correctamente
            System.out.println("Docente guardado correctamente");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public ListaEnlazada<Docente> busquedaPorNombreLineal(String nombre) {
        ListaEnlazada<Docente> lista = docenteDao.listar();
        ListaEnlazada<Docente> resultado = new ListaEnlazada<>();

        Docente[] arreglo = lista.toArray();

        quickSortNombre(arreglo, 0, arreglo.length - 1);

        for (Docente pc : arreglo) {
            if (pc.getPrimer_nombre().toLowerCase().startsWith(nombre.toLowerCase())) {
                resultado.insertar(pc);
            }
        }

        return resultado;
    }

    public ListaEnlazada<Docente> buscarPorNombreBinaria(String nombre) {
        ListaEnlazada<Docente> lista = docenteDao.listar();
        ListaEnlazada<Docente> resultado = new ListaEnlazada<>();

        Docente[] arreglo = lista.toArray();

        quickSortNombre(arreglo, 0, arreglo.length - 1);

        int l = 0, r = arreglo.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (arreglo[m].getPrimer_nombre().equalsIgnoreCase(nombre)) {
                resultado.insertar(arreglo[m]);
                return resultado;
            } else if (arreglo[m].getPrimer_nombre().compareToIgnoreCase(nombre) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        for (var a : arreglo) {
            System.out.println(a + " == " + nombre);
        }

        return resultado;
    }

    private void quickSortNombre(Docente[] arr, int low, int high) {
        if (low < high) {

            int pi = partitionNombre(arr, low, high);

            quickSortNombre(arr, low, pi - 1);
            quickSortNombre(arr, pi + 1, high);
        }
    }

    private int partitionNombre(Docente[] arr, int low, int high) {
        Docente pivot = arr[high];

        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            if (arr[j].getPrimer_nombre().compareToIgnoreCase(pivot.getPrimer_nombre()) < 0) {
                i++;
                swapNombre(arr, i, j);
            }
        }
        swapNombre(arr, i + 1, high);
        return (i + 1);
    }

    private void swapNombre(Docente[] arr, int i, int j) {
        Docente temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public ListaEnlazada<Docente> busquedaPorCedulaLineal(String cedula) {
        ListaEnlazada<Docente> lista = docenteDao.listar();
        ListaEnlazada<Docente> resultado = new ListaEnlazada<>();

        Docente[] arreglo = lista.toArray();

        quickSortNombre(arreglo, 0, arreglo.length - 1);

        for (Docente pc : arreglo) {
            if (pc.getPrimer_nombre().toLowerCase().startsWith(cedula.toLowerCase())) {
                resultado.insertar(pc);
            }
        }

        return resultado;
    }

    public ListaEnlazada<Docente> buscarPorCedulaBinaria(String cedula) {
        ListaEnlazada<Docente> lista = docenteDao.listar();
        ListaEnlazada<Docente> resultado = new ListaEnlazada<>();

        Docente[] arreglo = lista.toArray();

        quickSortNombre(arreglo, 0, arreglo.length - 1);

        int l = 0, r = arreglo.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (arreglo[m].getCedula().equalsIgnoreCase(cedula)) {
                resultado.insertar(arreglo[m]);
                return resultado;
            } else if (arreglo[m].getCedula().compareToIgnoreCase(cedula) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        for (var a : arreglo) {
            System.out.println(a + " == " + cedula);
        }

        return resultado;
    }

}
