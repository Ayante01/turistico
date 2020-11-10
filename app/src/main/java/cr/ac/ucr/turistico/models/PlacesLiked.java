package cr.ac.ucr.turistico.models;

import java.util.ArrayList;

public class PlacesLiked {

    private String userID;
    private String places = "";

    public PlacesLiked() {
    }

    public PlacesLiked(String userID, String places) {
        this.userID = userID;
        this.places = places;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "placesLiked{" +
                "userID='" + userID + '\'' +
                ", places=" + places +
                '}';
    }
}
