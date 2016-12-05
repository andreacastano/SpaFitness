package co.yolima.spafitness;


public class Citas {
    private String nombre;
    private String apellido;
    private int edad;
    private int activa;

    public Citas() {
    }

    public Citas(String nombre, String apellido, int edad, int activa) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.activa = activa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }
}
