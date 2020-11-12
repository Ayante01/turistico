package cr.ac.ucr.turistico.models;

import java.util.ArrayList;

public class PlacesLiked {

    private String userID;
    private ArrayList<Object> likes;

    public PlacesLiked() {
    }

    public PlacesLiked(String userID, ArrayList<Object> likes) {
        this.userID = userID;
        this.likes = likes;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Object> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Object> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "PlacesLiked{" +
                "userID='" + userID + '\'' +
                ", likes=" + likes +
                '}';
    }
}
