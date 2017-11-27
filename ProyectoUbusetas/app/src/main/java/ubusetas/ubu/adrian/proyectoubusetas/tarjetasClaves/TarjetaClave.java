package ubusetas.ubu.adrian.proyectoubusetas.tarjetasClaves;

/*
* @name: TarjetaClave
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que implementa el contenido de una tarjeta de claves dicotómicas.
* */

public class TarjetaClave {
    private long id;
    private String name;
    private int color_resource;

    /*
    * @name: getId
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que devuelve el id de la tarjeta.
    * @return: long, el id de la tarjeta
    * */

    public long getId() {
        return id;
    }

    /*
    * @name: setId
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que establece el id de la tarjeta
    * @param: long, el id de la tarjeta
    * */

    public void setId(long id) {
        this.id = id;
    }

    /*
    * @name: getName
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que devuelve el nombre de la tarjeta.
    * @return: String, el nombre de la tarjeta
    * */

    public String getName() {
        return name;
    }

    /*
    * @name: setName
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que establece el nombre de la tarjeta
    * @param: String, el nombre de la tarjeta
    * */

    public void setName(String name) {
        this.name = name;
    }

   /*
    * @name: getColorResource
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que devuelve el color de la tarjeta.
    * @return: int, el color de la tarjeta
    * */

    public int getColorResource() {
        return color_resource;
    }

    /*
    * @name: setColorResource
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que establece el color de la tarjeta
    * @param: int, el color de la tarjeta
    * */

    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }
}
