package controlador.ed.lista.exception;

public class IndexListException extends Exception{
    public IndexListException(Integer length) {
        super("This index doesn't exist in the list List["+ length +"]");
    }
}
