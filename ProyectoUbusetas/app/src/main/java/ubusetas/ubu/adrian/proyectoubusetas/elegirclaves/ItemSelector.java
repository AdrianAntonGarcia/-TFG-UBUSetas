package ubusetas.ubu.adrian.proyectoubusetas.elegirclaves;

/**
 * Clase que implementa los elementos del selector
 *
 * @author Adrián Antón García
 * @name ItemSelector
 * @category clase
 */

public class ItemSelector {

    //Texto del elemento
    private String texto;

    /**
     * Constructor que inicializa el elemento.
     *
     * @name ItemSelector
     * @author Adrián Antón García
     * @category constructor
     */

    public ItemSelector(String texto) {
        this.texto = texto;
    }

    /**
     * Método que devuelve el texto del elemento.
     *
     * @return String, texto del elemento.
     * @name getTexto
     * @author Adrián Antón García
     * @category método
     */

    public String getTexto() {
        return texto;
    }

    /**
     * Método que carga el texto del elemento.
     *
     * @param String, texto del elemento.
     * @name setTexto
     * @author Adrián Antón García
     * @category método
     */

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
