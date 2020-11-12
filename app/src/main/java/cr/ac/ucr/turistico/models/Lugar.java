package cr.ac.ucr.turistico.models;

public class Lugar {

    String place;
    String province;
    String info;
    String ubication;
    String category;
    String image;
    String latitude;
    String longitude;
    Long id;
    int likes;

    boolean transport;
    boolean wifi;
    boolean beach;
    boolean restaurant;
    boolean coffeeShop;

    public Lugar() {
    }

    public Lugar(String place, String province, String info, String ubication, String category, String image, String latitude, String longitude, Long id, int likes, boolean transport, boolean wifi, boolean beach, boolean restaurant, boolean coffeeShop) {
        this.place = place;
        this.province = province;
        this.info = info;
        this.ubication = ubication;
        this.category = category;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.likes = likes;
        this.transport = transport;
        this.wifi = wifi;
        this.beach = beach;
        this.restaurant = restaurant;
        this.coffeeShop = coffeeShop;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isTransport() {
        return transport;
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isBeach() {
        return beach;
    }

    public void setBeach(boolean beach) {
        this.beach = beach;
    }

    public boolean isRestaurant() {
        return restaurant;
    }

    public void setRestaurant(boolean restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isCoffeeShop() {
        return coffeeShop;
    }

    public void setCoffeeShop(boolean coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "place='" + place + '\'' +
                ", province='" + province + '\'' +
                ", info='" + info + '\'' +
                ", ubication='" + ubication + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", id=" + id +
                ", likes=" + likes +
                ", transport=" + transport +
                ", wifi=" + wifi +
                ", beach=" + beach +
                ", restaurant=" + restaurant +
                ", coffeeShop=" + coffeeShop +
                '}';
    }
}
