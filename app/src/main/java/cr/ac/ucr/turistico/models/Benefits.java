package cr.ac.ucr.turistico.models;

public class Benefits {

    private boolean transport;
    private boolean wifi;
    private boolean nearBeach;
    private boolean cafeteria;
    private boolean restaurants;

    public Benefits() {
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public void setNearBeach(boolean nearBeach) {
        this.nearBeach = nearBeach;
    }

    public void setCafeteria(boolean cafeteria) {
        this.cafeteria = cafeteria;
    }

    public void setRestaurants(boolean restaurants) {
        this.restaurants = restaurants;
    }

    public boolean isTransport() {
        return transport;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isNearBeach() {
        return nearBeach;
    }

    public boolean isCafeteria() {
        return cafeteria;
    }

    public boolean isRestaurants() {
        return restaurants;
    }

    @Override
    public String toString() {
        return "Benefits{" +
                "transport=" + transport +
                ", wifi=" + wifi +
                ", nearBeach=" + nearBeach +
                ", cafeteria=" + cafeteria +
                ", restaurants=" + restaurants +
                '}';
    }
}
