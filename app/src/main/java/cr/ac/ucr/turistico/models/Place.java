package cr.ac.ucr.turistico.models;

public class Place {

    private int id;
    private String name;
    private Location location;
    private String province;
    private String info;
    private Benefits benefits;
    private int likes;

    public Place() {
    }

    public Place(int id, String name, Location location, String province, String info, Benefits benefits, int likes) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.province = province;
        this.info = info;
        this.benefits = benefits;
        this.likes = likes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBenefits(Benefits benefits) {
        this.benefits = benefits;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getProvince() {
        return province;
    }

    public String getInfo() {
        return info;
    }

    public Benefits getBenefits() {
        return benefits;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", province='" + province + '\'' +
                ", info='" + info + '\'' +
                ", benefits=" + benefits +
                ", likes=" + likes +
                '}';
    }
}
