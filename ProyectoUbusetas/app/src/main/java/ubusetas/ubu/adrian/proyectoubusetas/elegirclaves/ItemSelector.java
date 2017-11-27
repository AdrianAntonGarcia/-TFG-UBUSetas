package ubusetas.ubu.adrian.proyectoubusetas.elegirclaves;

/*
* @name: ItemSelector
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que implementa los elementos del selector
* */

public class ItemSelector {

    //Texto del elemento
    private String texto;

    /*
    * @name: ItemSelector
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Constructor que inicializa el elemento.
    * */

    public ItemSelector(String texto) {
        this.texto = texto;
    }

    /*
    * @name: getTexto
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que devuelve el texto del elemento.
    * @return: String, texto del elemento.
    * */

    public String getTexto() {
        return texto;
    }

    /*
    * @name: setTexto
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que carga el texto del elemento.
    * @param: String, texto del elemento.
    * */

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
