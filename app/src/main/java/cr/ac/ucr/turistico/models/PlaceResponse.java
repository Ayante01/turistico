package cr.ac.ucr.turistico.models;

import java.util.ArrayList;

public class PlaceResponse {

    ArrayList<Place> results;

    public PlaceResponse() {
    }

    public PlaceResponse(ArrayList<Place> results) {
        this.results = results;
    }

    public void setResults(ArrayList<Place> results) {
        this.results = results;
    }

    public ArrayList<Place> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "PlaceResponse{" +
                "results=" + results +
                '}';
    }
}
