package ubusetas.ubu.adrian.proyectoubusetas.tarjetasclaves;

/**
 * Clase que implementa el contenido de una tarjeta de claves dicotómicas.
 *
 * @author Adrián Antón García
 * @name TarjetaClave
 * @category clase
 */

public class TarjetaClave {
    private long id;
    private String name;
    private int color_resource;

    /**
     * Método que devuelve el id de la tarjeta.
     *
     * @return long, el id de la tarjeta
     * @name getId
     * @author Adrián Antón García
     * @category método
     */

    public long getId() {
        return id;
    }

    /**
     * Procedimiento que establece el id de la tarjeta
     *
     * @param long, el id de la tarjeta
     * @name setId
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Método que devuelve el nombre de la tarjeta.
     *
     * @return String, el nombre de la tarjeta
     * @name getName
     * @author Adrián Antón García
     * @category método
     */

    public String getName() {
        return name;
    }

    /**
     * Procedimiento que establece el nombre de la tarjeta
     *
     * @param String, el nombre de la tarjeta
     * @name setName
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que devuelve el color de la tarjeta.
     *
     * @return int, el color de la tarjeta
     * @name getColorResource
     * @author Adrián Antón García
     * @category método
     */

    public int getColorResource() {
        return color_resource;
    }

    /**
     * Procedimiento que establece el color de la tarjeta
     *
     * @param int, el color de la tarjeta
     * @name setColorResource
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }
}
