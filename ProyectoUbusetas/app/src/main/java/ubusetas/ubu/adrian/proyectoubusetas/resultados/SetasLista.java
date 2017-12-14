package ubusetas.ubu.adrian.proyectoubusetas.resultados;

/*
* @name: SetasLista
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que implementa el contenido de que va a tener la lista de
* imagenes que se muestran como resultado trás clasificar una foto.
* */

public class SetasLista {

    //path de la imagen a cargar como icono de la lista

    public String path;

    //nombre de la seta clasificada como un resultado de la lista

    public String nombre;

        /*
    * @name: ItemSelector
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Constructor que inicializa el elemento.
    * @param: String, parh de la imágen
    * @param: String, nombre de la seta
    * */

    public SetasLista(String path, String nombre) {
        super();
        this.path = path;
        this.nombre = nombre;
    }

    /*
    * @name: getNombre
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Constructor que inicializa el elemento.
    * @param: String, parh de la imágen
    * @param: String, nombre de la seta
    * */

    public String getNombre(){
        return nombre;
    }

    public String getPath(){
        return path;
    }
}
