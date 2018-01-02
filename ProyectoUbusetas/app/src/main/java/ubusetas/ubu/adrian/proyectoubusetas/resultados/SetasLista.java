package ubusetas.ubu.adrian.proyectoubusetas.resultados;

/**
 * Clase que implementa el contenido de que va a tener la lista de
 * imágenes que se muestran como resultado tras clasificar una foto.
 *
 * @author Adrián Antón García
 * @name SetasLista
 * @category class
 */

public class SetasLista {

    //path de la imagen a cargar como icono de la lista

    public String path;

    //nombre de la seta clasificada como un resultado de la lista

    public String nombre;

    /**
     * Constructor que inicializa el elemento de la lista de resultados.
     *
     * @param String, parh de la imagen
     * @param String, nombre de la seta
     * @name ItemSelector
     * @author Adrián Antón García
     * @category constructor
     */

    public SetasLista(String path, String nombre) {
        super();
        this.path = path;
        this.nombre = nombre;
    }

    /**
     * Método que devuelve el nombre del elemento.
     *
     * @return String, nombre de la seta
     * @name getNombre
     * @author Adrián Antón García
     * @category método
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Método que devuelve el path del elemento.
     *
     * @return String, path de la imagen
     * @name getPath
     * @author Adrián Antón García
     * @category método
     */

    public String getPath() {
        return path;
    }
}
