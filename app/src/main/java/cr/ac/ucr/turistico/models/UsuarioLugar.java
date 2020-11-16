package cr.ac.ucr.turistico.models;

public class UsuarioLugar {

    private String idUser;
    private String idPlace;

    public UsuarioLugar() {
    }

    public UsuarioLugar(String idUser, String idPlace) {
        this.idUser = idUser;
        this.idPlace = idPlace;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPlace() {
        return idPlace;
    }

    @Override
    public String toString() {
        return "UsuarioLugar{" +
                "idUser='" + idUser + '\'' +
                ", idPlace='" + idPlace + '\'' +
                '}';
    }
}
