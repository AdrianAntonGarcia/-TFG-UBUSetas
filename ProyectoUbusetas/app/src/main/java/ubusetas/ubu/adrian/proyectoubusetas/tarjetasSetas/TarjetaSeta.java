package ubusetas.ubu.adrian.proyectoubusetas.tarjetasSetas;


import android.graphics.Bitmap;

public class TarjetaSeta {
    private long id;
    private String name;
    private int color_resource;
    private Bitmap imagenSeta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setImagenSeta(Bitmap imagen) {
        this.imagenSeta = imagen;
    }

    public Bitmap getImagenSeta() {
        return imagenSeta;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorResource() {
        return color_resource;
    }

    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }
}
