package cr.ac.ucr.turistico.models;

public class User {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String imgPerfil;

    public User() {
    }

    public User(String nombre, String apellido, String email, String password, String imgPerfil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.imgPerfil = imgPerfil;
    }

    //SETS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    //GETS

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    @Override
    public String toString() {
        return "User{" +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imgPerfil='" + imgPerfil + '\'' +
                '}';
    }
}
