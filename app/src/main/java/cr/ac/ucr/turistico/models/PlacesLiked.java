package cr.ac.ucr.turistico.models;

import java.util.ArrayList;

public class PlacesLiked {

    private String userID;
    private String placeID;

    public PlacesLiked() {
    }

    public PlacesLiked(String userID, String placeID) {
        this.userID = userID;
        this.placeID = placeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    @Override
    public String toString() {
        return "PlacesLiked{" +
                "userID='" + userID + '\'' +
                ", placeID=" + placeID +
                '}';
    }
}
